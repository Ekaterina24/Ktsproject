package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.greeting.GreetingScreen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginUiEvent
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModel
import com.rykova_e.kts_project.presentation.ui.screen.main.MainScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.MainViewModel
import com.rykova_e.kts_project.theme.AppThemeMaterial
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppThemeMaterial {
                MaterialTheme {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

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
                            val viewModel = viewModel { LoginViewModel() }
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(Unit) {
                                viewModel.events.collect { event ->
                                    when (event) {
                                        LoginUiEvent.LoginSuccessEvent -> navController.navigate(
                                            Screen.MainScreen.route
                                        ) {
                                            popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                        }

                                        is LoginUiEvent.LoginErrorEvent -> {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message = event.message)
                                            }
                                        }

                                        null -> {}
                                    }
                                }
                            }
                            LoginScreen(
                                snackbarHostState = snackbarHostState,
                                state = state,
                                onUsernameChanged = viewModel::onUsernameChanged,
                                onPasswordChanged = viewModel::onPasswordChanged,
                                login = viewModel::login
                            )
                        }
                        composable(Screen.MainScreen.route) {
                            val viewModel = viewModel { MainViewModel() }
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            MainScreen(
                                state = state,
                                loadPosts = viewModel::loadPosts
                            )
                        }
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