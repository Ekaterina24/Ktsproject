package com.rykova_e.kts_project.presentation.ui.navigation

sealed class Screen(val route: String) {

    object OnboardingScreen: Screen(ROUTE_ONBOARDING)
    object LoginScreen: Screen(ROUTE_LOGIN)
    object MainScreen: Screen(ROUTE_MAIN)

    companion object {
        const val ROUTE_ONBOARDING = "route_onboarding"
        const val ROUTE_LOGIN = "route_login"
        const val ROUTE_MAIN = "route_main"
    }
}