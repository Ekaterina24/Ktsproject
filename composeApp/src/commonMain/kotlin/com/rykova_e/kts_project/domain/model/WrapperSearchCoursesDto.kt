package com.rykova_e.kts_project.domain.model

data class WrapperSearchCoursesDto(
    val meta: MetaDataDto,
    val searchItems: List<SearchDto>
)