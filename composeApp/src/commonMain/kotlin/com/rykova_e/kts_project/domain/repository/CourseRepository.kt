package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CoursesDto
import com.rykova_e.kts_project.domain.model.SearchCoursesDto

interface CourseRepository {

    suspend fun getCourses(page: Int): Result<CoursesDto>
    suspend fun getCourse(id: Long): Result<CourseDto>
    suspend fun searchCourses(query: String, page: Int): Result<SearchCoursesDto>
    suspend fun getCoursesByIds(ids: List<Long>): Result<List<CourseDto>>
}