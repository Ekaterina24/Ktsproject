package com.rykova_e.kts_project.presentation.ui.screen.login.platform

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.rykova_e.kts_project.presentation.ui.navigation.Screen
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModelCommon
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginUiEvent
import kotlinx.coroutines.launch

@Composable
fun LoginEventsHandler(
    viewModel: LoginViewModelCommon,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                LoginUiEvent.LoginSuccessEvent -> {
                    navController.navigate(Screen.MainScreen) {
                        popUpTo(Screen.LoginScreen) { inclusive = true }
                    }
                }
                is LoginUiEvent.LoginErrorEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
                null -> {}
            }
        }
    }
}