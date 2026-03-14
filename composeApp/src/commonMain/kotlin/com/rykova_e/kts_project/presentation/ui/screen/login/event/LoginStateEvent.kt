package com.rykova_e.kts_project.presentation.ui.screen.login.event

sealed class LoginStateEvent {
    object OnOpenLoginPage: LoginStateEvent()

    class OnChangedCode(val value: String): LoginStateEvent()
    class OnChangedShowInputCode(val value: Boolean): LoginStateEvent()
    object OnAuthByCode: LoginStateEvent()
}