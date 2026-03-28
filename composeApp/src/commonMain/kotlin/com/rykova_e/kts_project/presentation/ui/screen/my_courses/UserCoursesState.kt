package com.rykova_e.kts_project.presentation.ui.screen.my_courses

import com.rykova_e.kts_project.presentation.ui.model.CourseModel

data class UserCoursesState(
    val courses: List<CourseModel> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)