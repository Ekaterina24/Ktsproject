package com.rykova_e.kts_project.navigation

sealed class Screen(val route: String) {

    object GreetingScreen: Screen(ROUTE_GREETING)
    object LoginScreen: Screen(ROUTE_LOGIN)

    companion object {
        const val ROUTE_GREETING = "route_greeting"
        const val ROUTE_LOGIN = "route_login"
    }
}