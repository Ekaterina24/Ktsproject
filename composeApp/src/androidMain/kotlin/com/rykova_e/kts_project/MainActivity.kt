package com.rykova_e.kts_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import com.rykova_e.kts_project.presentation.theme.AppThemeMaterial
import com.rykova_e.kts_project.presentation.ui.component.CircularProgressBarCustom
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.CourseListScreen
import com.rykova_e.kts_project.presentation.ui.screen.main.MainViewModel
import com.rykova_e.kts_project.presentation.ui.screen.onboarding.OnBoardingScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initContext(this)
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
                        loggedIn.isNotEmpty() -> Screen.MainScreen.route
                        firstOpen -> Screen.OnboardingScreen.route
                        else -> Screen.LoginScreen.route
                    }
                    isLoading = false
                }

                Box {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressBarCustom()
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Загрузка...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
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
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}