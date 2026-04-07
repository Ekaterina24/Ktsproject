package com.rykova_e.kts_project.presentation.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.data.source.local.db.repository.CourseRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.remote.UserRepositoryImpl
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel: ViewModel() {

    private val dataStore: SettingsStorage = DataStoreSettingsStorage()

    private val userRepository = UserRepositoryImpl()
    private val courseRepositoryLocal = CourseRepositoryLocalImpl()

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()


    fun getProfileData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching {
                userRepository.getUserProfile().toUI()
            }.onSuccess { user ->
                _state.update { it.copy(user = user, isLoading = false) }
            }.onFailure { e ->
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            courseRepositoryLocal.clearAllData()

            dataStore.saveAccessToken("")
            dataStore.saveRefreshToken("")
        }
    }

    fun reload() {
        getProfileData()
    }
}