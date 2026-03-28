package com.rykova_e.kts_project.presentation.ui.model

data class SearchCoursesModel(
    override val page: Int = 1,
    override val hasNext: Boolean = false,
    override val courses: List<CourseModel> = listOf(),
    val courseIds: List<SearchModel> = listOf(),
): CommonCoursesData(page, hasNext, courses)
