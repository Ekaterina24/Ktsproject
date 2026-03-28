package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CoursesDto
import com.rykova_e.kts_project.domain.model.SearchCoursesDto
import com.rykova_e.kts_project.domain.repository.CourseRepository

class CourseRepositoryImpl : CourseRepository {

    private val apiService = ApiService(Networking.httpClient)

    override suspend fun getCourses(page: Int): Result<CoursesDto> {
        return runCatching {
            apiService.getCourses(page).toDto()
        }
    }

    override suspend fun getCourse(id: Long): Result<CourseDto> {
        return runCatching {
            apiService.getCourseById(id).courses.first().toDto()
        }
    }

    override suspend fun getCoursesByIds(ids: List<Long>): Result<List<CourseDto>> {
        return runCatching {
            apiService.getCoursesByIds(ids).courses.map { it.toDto() }
        }
    }

    override suspend fun searchCourses(query: String, page: Int): Result<SearchCoursesDto> {
        return runCatching {
            apiService.searchCourses(query, page).toDto()
        }
    }
}
