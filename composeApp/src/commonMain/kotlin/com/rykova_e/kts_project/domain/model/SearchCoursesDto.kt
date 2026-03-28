package com.rykova_e.kts_project.domain.model

data class SearchCoursesDto(
    val page: Int,
    val hasNext: Boolean,
    val searchItems: List<SearchDto>
)