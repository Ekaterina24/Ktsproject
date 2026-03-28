package com.rykova_e.kts_project.presentation.ui.model

open class CommonCoursesData(
    open val page: Int,
    open val hasNext: Boolean,
    open val courses: List<CourseModel>
)