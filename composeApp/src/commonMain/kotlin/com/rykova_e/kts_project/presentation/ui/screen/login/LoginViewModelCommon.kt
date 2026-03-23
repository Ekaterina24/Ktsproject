package com.rykova_e.kts_project.presentation.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.domain.repository.PlatformAuthService
import com.rykova_e.kts_project.domain.repository.PlatformIntent
import com.rykova_e.kts_project.domain.use_case.AuthUseCase
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginStateEvent
import com.rykova_e.kts_project.presentation.ui.screen.login.event.LoginUiEvent
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModelCommon(
    private val authUseCase: AuthUseCase,
    private val platformAuthService: PlatformAuthService,
    private val toastChannel: PlatformChannel<String>,
    private val openAuthPageChannel: PlatformChannel<PlatformIntent>,
    private val authSuccessChannel: PlatformChannel<Unit>,
    private val dataStore: SettingsStorage
) : LoginViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    override val state = _state.asStateFlow()

    private val _loadingFlow = MutableStateFlow(false)
    override val loadingFlow: StateFlow<Boolean> = _loadingFlow.asStateFlow()

    override val authSuccessFlow: Flow<Unit> = authSuccessChannel.receiveAsFlow()
    override val toastFlow: Flow<String> = toastChannel.receiveAsFlow()
    override val openAuthPageFlow: Flow<PlatformIntent> = openAuthPageChannel.receiveAsFlow()

    private val _events = MutableSharedFlow<LoginUiEvent?>()
    override val events = _events.asSharedFlow()

    override fun login() {
        openLoginPage()
    }

    override fun exchangeCodeForTokens() {
        viewModelScope.launch {
            _loadingFlow.value = true
            runCatching {
                val tokens = authUseCase.exchangeCodeForTokens(
                    platformAuthService,
                    _state.value.code
                )
                Napier.d("token ${tokens.accessToken}", tag = "TAG")
                dataStore.saveAccessToken(tokens.accessToken)
                dataStore.saveRefreshToken(tokens.refreshToken)
            }.onSuccess {
                _loadingFlow.value = false
                _events.emit(LoginUiEvent.LoginSuccessEvent)
                authSuccessChannel.send(Unit)
            }.onFailure {
                _loadingFlow.value = false
                _events.emit(LoginUiEvent.LoginErrorEvent(it.message ?: "Неизвестная ошибка"))
                toastChannel.send("Ошибка авторизации")
            }
        }
    }

    private fun openLoginPage() {
        val authRequest = authUseCase.getAuthRequest()
        val intent = platformAuthService.getAuthorizationRequestIntent(authRequest)
        openAuthPageChannel.send(intent)
        onLoginStateEvent(LoginStateEvent.OnChangedShowInputCode(true))
    }

    override fun onAuthCodeFailed() {
        toastChannel.send("Авторизация отменена")
    }

    override fun onLoginStateEvent(event: LoginStateEvent) {
        when (event) {
            LoginStateEvent.OnOpenLoginPage -> login()
            is LoginStateEvent.OnChangedCode -> _state.update { it.copy(code = event.value) }
            is LoginStateEvent.OnChangedShowInputCode -> _state.update { it.copy(isShowInputCode = event.value) }
            LoginStateEvent.OnAuthByCode -> exchangeCodeForTokens()
        }
    }
}