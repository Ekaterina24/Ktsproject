package com.rykova_e.kts_project.presentation.ui.screen.main.detail

sealed class OnEventCourseDetail {
    data class singUpOnCourse(val courseId: Long): OnEventCourseDetail()
}