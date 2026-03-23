package com.rykova_e.kts_project.presentation.di

import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModelCommon
import com.rykova_e.kts_project.presentation.ui.screen.main.MainViewModel
import com.rykova_e.kts_project.presentation.ui.screen.profile.UserProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val platformViewModelModule = module {

    viewModel {
        LoginViewModelCommon(
            authUseCase = get(),
            platformAuthService = get(),
            toastChannel = get(),
            openAuthPageChannel = get(),
            authSuccessChannel = get(),
            dataStore = get()
        )
    }

    viewModel {
        MainViewModel(
            courseRepositoryCommon = get()
        )
    }

    viewModel {
        UserProfileViewModel(
            dataStore = get(),
            userRepository = get(),
            courseRepositoryLocal = get()
        )
    }
}