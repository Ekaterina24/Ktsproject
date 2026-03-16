package com.rykova_e.kts_project.navigation

sealed class Screen(val route: String) {

    object GreetingScreen: Screen(ROUTE_GREETING)
    object LoginScreen: Screen(ROUTE_LOGIN)
    object MainScreen: Screen(ROUTE_MAIN)

    companion object {
        const val ROUTE_GREETING = "route_greeting"
        const val ROUTE_LOGIN = "route_login"
        const val ROUTE_MAIN = "route_main"
    }
}