package com.rykova_e.kts_project.presentation.ui.screen.main.detail

import com.rykova_e.kts_project.presentation.ui.model.CourseModel

data class CourseDetailState(
    val course: CourseModel = CourseModel(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val toast: String? = null,
    val isRecord: Boolean = false
)
