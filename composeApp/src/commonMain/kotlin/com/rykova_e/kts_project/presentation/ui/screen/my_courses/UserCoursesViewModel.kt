package com.rykova_e.kts_project.presentation.ui.screen.my_courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.domain.repository.CourseRepository
import com.rykova_e.kts_project.domain.repository.UserRepository
import com.rykova_e.kts_project.domain.use_case.GetUserCoursesUseCase
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.utils.roundToDecimal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class UserCoursesViewModel(
    private val getUserCoursesUseCase: GetUserCoursesUseCase,
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository,
): ViewModel() {

    private val _state = MutableStateFlow(UserCoursesState())
    val state = _state.asStateFlow()

    init {
        getUserCourses()
    }

    fun getUserCourses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching {
                val coursesId = getUserCoursesUseCase.execute()
                val userCourses = courseRepository.getCoursesByIds(coursesId.map { it.courseId })
                val courses = getCoursesWithAuthors(userCourses.map { it.toUI() })
                courses
            }.onSuccess { courses ->
                _state.update { it.copy(courses = courses, isLoading = false) }
            }.onFailure { e ->
                if (e is CancellationException) throw e
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private suspend fun getCoursesWithAuthors(courses: List<CourseModel>): List<CourseModel> {
        val authorsId = courses.flatMap { it.authors.map { it.id } }
        val authors = userRepository.getUsersByIds(authorsId)

        val authorsUI = authors.map { it.toUI() }
        val authorsMap = authorsUI.associateBy { it.id }

        val ratingIds = courses.map { it.rating?.toLong() ?: 0 }
        val reviews = courseRepository.getReviewsByCourseIds(ratingIds)

        val reviewsMap = reviews.associateBy { it.courseId }

        val coursesWithAuthors = courses.map { course ->
            val courseAuthors = course.authors.mapNotNull { author ->
                authorsMap[author.id]
            }
            course.copy(authors = courseAuthors)
        }

        val coursesWithReviews = coursesWithAuthors.map { course ->
            val courseReview = reviewsMap[course.id.toString()]
            course.copy(rating = courseReview?.averageReview?.roundToDecimal() ?: "")
        }

        return coursesWithReviews
    }
}