package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.greeting.GreetingScreen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.CourseListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppThemeMaterial {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.GreetingScreen
                ) {
                    composable<Screen.GreetingScreen> {
                        GreetingScreen(
                            navigateToLoginScreen = {
                                navController.navigate(Screen.LoginScreen) {
                                    popUpTo(Screen.GreetingScreen) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable<Screen.LoginScreen> {
                        LoginScreen(
                            navController = navController,
                        )
                    }
                    composable<Screen.MainScreen> {
                        CourseListScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}