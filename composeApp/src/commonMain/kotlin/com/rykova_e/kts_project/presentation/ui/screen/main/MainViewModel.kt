package com.rykova_e.kts_project.presentation.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.data.source.remote.CourseRepositoryImpl
import com.rykova_e.kts_project.data.source.remote.UserRepositoryImpl
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperCoursesModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperSearchCoursesModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel: ViewModel() {

    private val courseRepository = CourseRepositoryImpl()
    private val userRepository = UserRepositoryImpl()

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    private val _searchFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _searchFlow
                .debounce(300L)
                .distinctUntilChanged()
                .collect { search ->
                    loadAndSearchCourses(search)
                }
        }
    }

    fun loadAndSearchCourses(search: String, page: Int = 1) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching {
                val query = search.trim()
                if (query.isEmpty()) {
                    _state.update { it.copy(search = "", courses = emptyList()) }
                    val coursesWrapper = courseRepository.getCourses(page = 1).toUI()
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
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            courses = (coursesWrapper as? WrapperCoursesModel)?.courses ?: emptyList(),
                            isLoading = false,
                            hasNextPage = (coursesWrapper as? WrapperCoursesModel)?.metaData?.has_next ?: false,
                            currentPage = 1,
                        )
                    }
                }
            }.onFailure { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(error = "Ошибка при получении курсов") }
                Napier.e("LoadCourses error", error, tag = "Network")
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
        val authors = userRepository.getUsersByIds(authorsId).map { it.toUI() }
        val authorsMap = authors.associateBy { it.id }

        val coursesWithAuthors = courses.map { course ->
            val courseAuthors = course.authors.mapNotNull { author ->
                authorsMap[author.id]
            }
            course.copy(authors = courseAuthors)
        }
        return coursesWithAuthors
    }

    fun onChangedSearch(value: String) {
        _state.update { it.copy(search = value, error = null) }
        _searchFlow.value = value
    }

    fun reload() {
        loadAndSearchCourses(_state.value.search)
    }
}