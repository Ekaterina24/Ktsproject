package com.rykova_e.kts_project.data.source

import com.rykova_e.kts_project.domain.repository.CourseRepository
import com.rykova_e.kts_project.domain.repository.UserRepository
import com.rykova_e.kts_project.domain.repository.local.CourseRepositoryLocal
import com.rykova_e.kts_project.domain.repository.local.ReviewRepositoryLocal
import com.rykova_e.kts_project.domain.repository.local.UserRepositoryLocal
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.utils.roundToDecimal

class GetCourseRepositoryCommonImpl(
    private val courseRepository: CourseRepository,
    private val courseRepositoryLocal: CourseRepositoryLocal,
    private val userRepository: UserRepository,
    private val userRepositoryLocal: UserRepositoryLocal,
    private val reviewRepositoryLocal: ReviewRepositoryLocal,
) {

    suspend fun getCourseLocalOrNetwork(id: Long, online: Boolean = true): CourseModel {
        return when {
            online -> getCourseByIdWithAuthors(id)
            else -> getLocalCourseByIdWithAuthors(id)
        }
    }

    private suspend fun getCourseByIdWithAuthors(courseId: Long): CourseModel {
        val course = courseRepository.getCourseById(courseId)
        val authorsId = course.authors
        val authors = userRepository.getUsersByIds(authorsId)
        val reviews = courseRepository.getReviewCourse(course.rating?.toLong() ?: 0L)

        val courseWithAuthorAndReview = course.toUI().copy(
            authors = authors.map { it.toUI() },
            rating = reviews.averageReview.roundToDecimal()
        )
        return courseWithAuthorAndReview
    }

    private suspend fun getLocalCourseByIdWithAuthors(courseId: Long): CourseModel {
        TODO()
    }
}