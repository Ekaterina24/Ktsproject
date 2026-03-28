package com.rykova_e.kts_project.presentation.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.domain.repository.LoginRepository
import com.rykova_e.kts_project.presentation.ui.mapper.getErrorMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    private val _state = MutableStateFlow(LoginUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()


    fun onUsernameChanged(value: String) {
        _state.update { it.copy(username = value) }
    }

    fun onPasswordChanged(value: String) {
        _state.update { it.copy(password = value) }
    }

    fun login() {
        viewModelScope.launch {
            loginRepository.login(
                username = _state.value.username,
                password = _state.value.password
            ).fold(
                onSuccess = { _ -> _events.emit(LoginUiEvent.LoginSuccessEvent) },
                onFailure = { result ->
                    _events.emit(
                        LoginUiEvent.LoginErrorEvent(result.getErrorMessage())
                    )
                }
            )
        }
    }
}