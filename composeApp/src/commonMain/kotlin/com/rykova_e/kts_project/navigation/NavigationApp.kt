package com.rykova_e.kts_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationApp(
    navController: NavHostController,
    greetingScreen: @Composable () -> Unit,
    loginScreen: @Composable () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GreetingScreen.route
    ) {
        composable(Screen.GreetingScreen.route) {
            greetingScreen()
        }
        composable(Screen.LoginScreen.route) {
            loginScreen()
        }
    }
}