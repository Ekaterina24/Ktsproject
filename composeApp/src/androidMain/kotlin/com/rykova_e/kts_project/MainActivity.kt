package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.greeting.GreetingScreen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.CourseListScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppThemeMaterial {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                NavHost(
                    navController = navController,
                    startDestination = Screen.GreetingScreen.route
                ) {
                    composable(Screen.GreetingScreen.route) {
                        GreetingScreen(
                            navigateToLoginScreen = {
                                navController.navigate(Screen.LoginScreen.route) {
                                    popUpTo(Screen.GreetingScreen.route) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(
                            snackbarHostState = snackbarHostState,
                            navController = navController
                        )
                    }
                    composable(Screen.MainScreen.route) {
                        val viewModel = viewModel { MainViewModel() }
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        CourseListScreen(
                            state = state,
                            loadMore = viewModel::loadMoreCourses,
                            onChangedSearch = viewModel::onChangedSearch,
                            reload = viewModel::reload
                        )
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