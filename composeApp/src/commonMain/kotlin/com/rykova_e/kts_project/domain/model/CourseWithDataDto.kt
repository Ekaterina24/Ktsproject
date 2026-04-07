package com.rykova_e.kts_project.domain.model

data class CourseWithDataDto(
    val course: CourseDto,
    val authors: List<UserDto>,
    val review: ReviewDto?
)
