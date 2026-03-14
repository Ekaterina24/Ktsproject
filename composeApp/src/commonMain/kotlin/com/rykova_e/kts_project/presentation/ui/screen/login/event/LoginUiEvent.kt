package com.rykova_e.kts_project.presentation.ui.screen.login.event

sealed class LoginUiEvent {

    object LoginSuccessEvent: LoginUiEvent()
    class LoginErrorEvent(val message: String): LoginUiEvent()
}