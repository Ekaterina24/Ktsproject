package com.rykova_e.kts_project.presentation.ui.screen.profile

import com.rykova_e.kts_project.presentation.ui.model.UserProfileModel

data class UserProfileState(
    val user: UserProfileModel = UserProfileModel(),
    val isLoading: Boolean = false,
    val error: String? = null
)
