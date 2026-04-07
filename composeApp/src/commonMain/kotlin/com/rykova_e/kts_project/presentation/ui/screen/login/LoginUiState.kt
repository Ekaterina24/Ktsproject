package com.rykova_e.kts_project.presentation.ui.screen.login

data class LoginUiState(
    val code: String = "",
    val isShowInputCode: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = ""
)