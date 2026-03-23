package com.rykova_e.kts_project.presentation.ui.screen.login

import androidx.lifecycle.ViewModel
import com.rykova_e.kts_project.domain.repository.PlatformIntent
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginStateEvent
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginUiEvent
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class LoginViewModel: ViewModel() {
    abstract val loadingFlow: StateFlow<Boolean>
    abstract val toastFlow: Flow<String>
    abstract val authSuccessFlow: Flow<Unit>
    abstract val events: SharedFlow<LoginUiEvent?>
    abstract val state: StateFlow<LoginUiState>
    abstract val openAuthPageFlow: Flow<PlatformIntent>

    abstract fun login()
    abstract fun onAuthCodeFailed()
    abstract fun exchangeCodeForTokens()
    abstract fun onLoginStateEvent(event: LoginStateEvent)
}

interface PlatformChannel<T> {
    fun send(data: T): ChannelResult<Unit>
    fun receiveAsFlow(): Flow<T>
}