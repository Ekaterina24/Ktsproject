package com.rykova_e.kts_project.domain.model

data class WrapperCoursesDto(
    val meta: MetaDataDto,
    val courses: List<CourseDto>
)