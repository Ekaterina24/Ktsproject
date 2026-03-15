package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.NetworkMonitor
import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.data.source.local.db.repository.CourseRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.ReviewRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.UserRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.remote.CourseRepositoryImpl
import com.rykova_e.kts_project.data.source.remote.UserRepositoryImpl
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.presentation.ui.mapper.toDto
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperCoursesModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperSearchCoursesModel
import com.rykova_e.kts_project.utils.roundToDecimal
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel: ViewModel() {

    private val courseRepository = CourseRepositoryImpl()
    private val courseRepositoryLocal = CourseRepositoryLocalImpl()
    private val userRepository = UserRepositoryImpl()
    private val userRepositoryLocal = UserRepositoryLocalImpl()
    private val reviewRepositoryLocal = ReviewRepositoryLocalImpl()

    private val networkMonitor = NetworkMonitor()
    val isOnlineFlow = networkMonitor.isConnected

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    private val _searchFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _searchFlow
                .combine(isOnlineFlow) { search, online -> search to online }
                .debounce(300L)
                .distinctUntilChanged()
                .collect { (search, online) ->
                    loadAndSearchCourses(search, online = online)
                }
        }
    }

    fun getCoursesLocal(search: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            courseRepositoryLocal.searchCoursesData(search)
                .onSuccess { courses ->
                    courses.collect { list ->
                        _state.update {
                            it.copy(
                                courses = list.map { it.toUI() },
                                isLoading = false,
                                hasNextPage = false,
                                currentPage = 1,
                            )
                        }
                    }
                }
                .onFailure { error ->
                    if (error is CancellationException) throw error
                    _state.update { it.copy(error = "Ошибка при получении курсов из базы") }
                    Napier.e("GetCourses error", error, tag = "DB")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            courses = emptyList(),
                            error = error.message ?: "Unknown error"
                        )
                    }
                }
        }
    }

    fun loadAndSearchCourses(search: String, page: Int = 1, online: Boolean) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            if (!online) {
                getCoursesLocal(search)
                return@launch
            }

            runCatching {
                val query = search.trim()
                if (query.isEmpty()) {
                    _state.update { it.copy(search = "", courses = emptyList()) }
                    val coursesWrapper = courseRepository.getCourses(page = 1).toUI()
                    saveCoursesLocal(coursesWrapper.courses.map { it.toDto() })

                    val coursesWithAuthor = getCoursesWithAuthors(coursesWrapper.courses)
                    coursesWrapper.copy(courses = coursesWithAuthor)
                } else {
                    val searchWrapper = courseRepository.searchCourses(search, page).toUI()
                    val getCourses = courseRepository.getCoursesByIds(searchWrapper.courseIds.map { it.course }).map { it.toUI() }
                    val coursesWithAuthor = getCoursesWithAuthors(getCourses)
                    searchWrapper.copy(courses = coursesWithAuthor)
                }
            }.onSuccess { coursesWrapper ->
                if (_searchFlow.value.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            courses = (coursesWrapper as? WrapperSearchCoursesModel)?.courses ?: emptyList(),
                            isLoading = false,
                            hasNextPage = (coursesWrapper as? WrapperSearchCoursesModel)?.metaData?.has_next ?: false,
                            currentPage = 1,
                            isRefreshing = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            courses = (coursesWrapper as? WrapperCoursesModel)?.courses ?: emptyList(),
                            isLoading = false,
                            hasNextPage = (coursesWrapper as? WrapperCoursesModel)?.metaData?.has_next ?: false,
                            currentPage = 1,
                            isRefreshing = false
                        )
                    }
                }
            }.onFailure { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(error = "Ошибка при получении курсов", isRefreshing = false) }
                Napier.e("LoadCourses error", error, tag = "Network")
                _state.update {
                    it.copy(
                        isLoading = false,
                        courses = emptyList(),
                        error = error.message ?: "Unknown error",
                        isRefreshing = false
                    )
                }
            }
        }
    }

    fun loadMoreCourses() {
        if (_state.value.isLoadingMore || !_state.value.hasNextPage) return
        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            val nextPage = _state.value.currentPage + 1
            runCatching {
                if (_searchFlow.value.isNotEmpty()) {
                    val coursesWrapper = courseRepository.searchCourses(
                        query = _searchFlow.value,
                        page = nextPage
                    ).toUI()
                    val getCourses = courseRepository.getCoursesByIds(coursesWrapper.courseIds.map { it.course }).map { it.toUI() }
                    val coursesWithAuthor = getCoursesWithAuthors(getCourses)
                    coursesWrapper.copy(courses = coursesWithAuthor)
                } else {
                    val coursesWrapper = courseRepository.getCourses(page = nextPage).toUI()
                    saveCoursesLocal(coursesWrapper.courses.map { it.toDto() })
                    val coursesWithAuthor = getCoursesWithAuthors(coursesWrapper.courses)
                    coursesWrapper.copy(courses = coursesWithAuthor)
                }
            }.onSuccess { result ->
                if (_searchFlow.value.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            courses = it.courses + ((result as? WrapperSearchCoursesModel)?.courses ?: emptyList()),
                            isLoadingMore = false,
                            hasNextPage = (result as? WrapperSearchCoursesModel)?.metaData?.has_next ?: false,
                            currentPage = nextPage,
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            courses = it.courses + ((result as? WrapperCoursesModel)?.courses ?: emptyList()),
                            isLoadingMore = false,
                            hasNextPage = (result as? WrapperCoursesModel)?.metaData?.has_next ?: false,
                            currentPage = nextPage,
                        )
                    }
                }
            }.onFailure { error ->
                _state.update { it.copy(error = "Ошибка при получении курсов") }
                Napier.e("LoadMoreCourses error", error, tag = "Network")

                _state.update {
                    it.copy(
                        isLoadingMore = false,
                        courses = it.courses,
                        error = error.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    private suspend fun getCoursesWithAuthors(courses: List<CourseModel>): List<CourseModel> {
        val authorsId = courses.flatMap { it.authors.map { it.id } }
        val authors = userRepository.getUsersByIds(authorsId)
        userRepositoryLocal.saveUsers(authors)

        // связи авторов
        val authorCrossRefs = courses.flatMap { course ->
            course.authors.map { author ->
                CourseAuthorCrossRef(course.id, author.id)
            }
        }
        courseRepositoryLocal.insertCourseAuthors(authorCrossRefs)

        val authorsUI = authors.map { it.toUI() }
        val authorsMap = authorsUI.associateBy { it.id }

        val ratingIds = courses.map { it.rating?.toLong() ?: 0 }
        val reviews = courseRepository.getReviewsByCourseIds(ratingIds)
        reviewRepositoryLocal.saveReviews(reviews)

        val reviewsMap = reviews.associateBy { it.courseId }

        val coursesWithAuthors = courses.map { course ->
            val courseAuthors = course.authors.mapNotNull { author ->
                authorsMap[author.id]
            }
            course.copy(authors = courseAuthors)
        }

        // связи отзывов
        val reviewCrossRefs = reviews.map { review ->
            CourseReviewCrossRef(review.courseId.toLong(), review.id)
        }
        courseRepositoryLocal.insertCourseReviews(reviewCrossRefs)

        val coursesWithReviews = coursesWithAuthors.map { course ->
            val courseReview = reviewsMap[course.id.toString()]
            course.copy(rating = courseReview?.averageReview?.roundToDecimal() ?: "")
        }

        return coursesWithReviews
    }

    fun onChangedSearch(value: String) {
        _state.update { it.copy(search = value, error = null) }
        _searchFlow.value = value
    }

    fun reload() {
        if (isOnlineFlow.value) {
            _state.update { it.copy(isRefreshing = true) }
            loadAndSearchCourses(
                search = _state.value.search,
                page = _state.value.currentPage,
                online = isOnlineFlow.value
            )
        } else {
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    suspend fun saveCoursesLocal(courses: List<CourseDto>) {
        courseRepositoryLocal.saveCourses(courses)
    }
}