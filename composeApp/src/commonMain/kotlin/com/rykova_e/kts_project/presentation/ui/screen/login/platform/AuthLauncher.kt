package com.rykova_e.kts_project.presentation.ui.screen.login.platform

import androidx.compose.runtime.Composable
import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModel

@Composable
expect fun AuthLauncher(
    viewModel: LoginViewModel
)