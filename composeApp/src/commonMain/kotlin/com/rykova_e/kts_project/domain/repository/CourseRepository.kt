package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.WrapperCoursesDto
import com.rykova_e.kts_project.domain.model.WrapperSearchCoursesDto

interface CourseRepository {

    suspend fun getCourses(page: Int): WrapperCoursesDto
    suspend fun getCourse(id: Long): CourseDto
    suspend fun searchCourses(query: String, page: Int): WrapperSearchCoursesDto
    suspend fun getCoursesByIds(ids: List<Long>): List<CourseDto>
}