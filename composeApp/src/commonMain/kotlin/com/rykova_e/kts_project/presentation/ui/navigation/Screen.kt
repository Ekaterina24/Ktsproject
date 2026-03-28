package com.rykova_e.kts_project.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object GreetingScreen: Screen()
    @Serializable
    object LoginScreen: Screen()
    @Serializable
    object MainScreen: Screen()

    companion object {
    }
}