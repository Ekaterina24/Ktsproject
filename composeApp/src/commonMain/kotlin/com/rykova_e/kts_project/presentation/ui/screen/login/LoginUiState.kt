package com.rykova_e.kts_project.presentation.ui.screen.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoginButtonActive: Boolean = false,
)
