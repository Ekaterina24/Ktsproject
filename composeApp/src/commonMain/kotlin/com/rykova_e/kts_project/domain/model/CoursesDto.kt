package com.rykova_e.kts_project.domain.model

data class CoursesDto(
    val page: Int,
    val hasNext: Boolean,
    val courses: List<CourseDto>
)