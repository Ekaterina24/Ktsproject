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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.ui.component.CustomLoader
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.CourseListScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.MainContainer
import com.rykova_e.kts_project.presentation.ui.screen.main.detail.CourseDetailScreen
import com.rykova_e.kts_project.presentation.ui.screen.onboarding.OnBoardingScreen
import com.rykova_e.kts_project.presentation.ui.screen.profile.UserProfileScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val dataStore: SettingsStorage by inject()
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
                var startDestination by remember { mutableStateOf<Screen>(Screen.OnboardingScreen) }

                val firstOpen by dataStore.observeFirstOpen()
                    .collectAsStateWithLifecycle(initialValue = true)

                val loggedIn by dataStore.observeAccessToken()
                    .collectAsStateWithLifecycle(initialValue = "")

                LaunchedEffect(firstOpen, loggedIn) {
                    isLoading = true
                    delay(500)

                    startDestination = when {
                        loggedIn.isNotEmpty() -> Screen.MainScreen
                        firstOpen -> Screen.OnboardingScreen
                        else -> Screen.LoginScreen
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
                            composable<Screen.OnboardingScreen> {
                                var isNextNavigate by remember { mutableStateOf(false) }

                                LaunchedEffect(isNextNavigate) {
                                    if (isNextNavigate) dataStore.setFirstOpen()
                                }

                                OnBoardingScreen(
                                    navigateToLoginScreen = {
                                        isNextNavigate = true
                                        navController.navigate(Screen.LoginScreen) {
                                            popUpTo(Screen.OnboardingScreen) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }
                            composable<Screen.LoginScreen> {
                                LoginScreen(
                                    snackbarHostState = snackbarHostState,
                                    navController = navController
                                )
                            }

                            composable<Screen.MainScreen> {
                                MainContainer(
                                    content = { modifier ->
                                        CourseListScreen(
                                            modifier = modifier,
                                            navController = navController
                                        )
                                    },
                                    navController = navController
                                )
                            }
                            composable<Screen.ProfileScreen> {
                                MainContainer(
                                    content = { modifier ->
                                        UserProfileScreen(
                                            modifier = modifier,
                                            navigateToLogin = {
                                                navController.navigate(Screen.LoginScreen)
                                            }
                                        )
                                    },
                                    navController = navController
                                )
                            }

                            composable<Screen.DetailCourseScreen> { backStackEntry ->
                                val courseId = backStackEntry.toRoute<Screen.DetailCourseScreen>().courseId
                                MainContainer(
                                    content = { modifier ->
                                        CourseDetailScreen(
                                            modifier = modifier,
                                            courseId = courseId,
                                            snackbarHostState = snackbarHostState
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