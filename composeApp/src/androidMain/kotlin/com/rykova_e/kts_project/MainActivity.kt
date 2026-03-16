package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.ui.component.CustomLoader
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.navigation.Screen.Companion.MAIN_GRAPH
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.CourseListScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.MainContainer
import com.rykova_e.kts_project.presentation.ui.screen.onboarding.OnBoardingScreen
import com.rykova_e.kts_project.presentation.ui.screen.profile.UserProfileScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initContext(this)
        Napier.base(DebugAntilog())
        setContent {
            AppThemeMaterial {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                var isLoading by remember { mutableStateOf(true) }
                var startDestination by remember { mutableStateOf("") }
                val dataStore = remember { DataStoreSettingsStorage() }
                val firstOpen by dataStore.observeFirstOpen()
                    .collectAsStateWithLifecycle(initialValue = true)

                val loggedIn by dataStore.observeAccessToken()
                    .collectAsStateWithLifecycle(initialValue = "")

                LaunchedEffect(firstOpen, loggedIn) {
                    isLoading = true
                    delay(500)

                    startDestination = when {
                        loggedIn.isNotEmpty() -> MAIN_GRAPH
                        firstOpen -> Screen.OnboardingScreen.route
                        else -> Screen.LoginScreen.route
                    }
                    isLoading = false
                }

                Box {
                    if (isLoading) {
                        CustomLoader()
                    } else {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(Screen.OnboardingScreen.route) {
                                var isNextNavigate by remember { mutableStateOf(false) }

                                LaunchedEffect(isNextNavigate) {
                                    if (isNextNavigate) dataStore.setFirstOpen()
                                }

                                OnBoardingScreen(
                                    navigateToLoginScreen = {
                                        isNextNavigate = true
                                        navController.navigate(Screen.LoginScreen.route) {
                                            popUpTo(Screen.OnboardingScreen.route) {
                                                inclusive = true
                                            }
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

                            navigation(
                                route = MAIN_GRAPH,
                                startDestination = Screen.MainScreen.route
                            ) {
                                composable(Screen.MainScreen.route) {
                                    MainContainer(
                                        content = { modifier ->
                                            CourseListScreen(
                                                modifier = modifier,
                                            )
                                        },
                                        navController = navController
                                    )
                                }
                                composable(Screen.ProfileScreen.route) {
                                    MainContainer(
                                        content = { modifier ->
                                            UserProfileScreen(
                                                modifier = modifier,
                                                navigateToLogin = {
                                                    navController.navigate(Screen.LoginScreen.route)
                                                }
                                            )
                                        },
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}