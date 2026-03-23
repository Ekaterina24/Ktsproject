package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.ReviewDto
import com.rykova_e.kts_project.domain.model.WrapperCoursesDto
import com.rykova_e.kts_project.domain.model.WrapperSearchCoursesDto
import com.rykova_e.kts_project.domain.repository.CourseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CourseRepositoryImpl(
    private val apiService: ApiService
): CourseRepository {

    override suspend fun getCourses(page: Int): WrapperCoursesDto {
        return apiService.getCourses(page).toDto()
    }

    override suspend fun getCourse(id: Long): CourseDto {
        return apiService.getCourseById(id).courses.first().toDto()
    }

    override suspend fun getCoursesByIds(ids: List<Long>): List<CourseDto> {
        return coroutineScope {
            val courses = ids.map { id ->
                async {
                    getCourse(id)
                }
            }
            courses.awaitAll()
        }
    }

    override suspend fun searchCourses(query: String, page: Int): WrapperSearchCoursesDto {
        return apiService.searchCourses(query, page).toDto()
    }

    override suspend fun getReviewCourse(id: Long): ReviewDto {
        return apiService.getReviewCourseById(id).reviews.first().toDto()
    }

    override suspend fun getReviewsByCourseIds(ids: List<Long>): List<ReviewDto> {
        return coroutineScope {
            val reviews = ids.map { id ->
                async {
                    getReviewCourse(id)
                }
            }
            reviews.awaitAll()
        }
    }
}