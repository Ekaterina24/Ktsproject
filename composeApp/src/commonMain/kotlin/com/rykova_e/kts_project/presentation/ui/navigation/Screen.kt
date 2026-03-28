package com.rykova_e.kts_project.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object OnboardingScreen: Screen()

    @Serializable
    object LoginScreen: Screen()

    @Serializable
    object MainScreen: Screen()

    @Serializable
    object ProfileScreen: Screen()

    @Serializable
    data class DetailCourseScreen(val courseId: Long) : Screen()

    @Serializable
    object UserCoursesScreen: Screen()

    companion object {
        const val ROUTE_DETAIL_COURSE = "route_detail_course"
    }
}