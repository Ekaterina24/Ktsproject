package com.rykova_e.kts_project.presentation.di

import com.rykova_e.kts_project.presentation.ui.screen.login.LoginViewModelCommon
import com.rykova_e.kts_project.presentation.ui.screen.main.MainViewModel
import com.rykova_e.kts_project.presentation.ui.screen.main.detail.CourseDetailViewModel
import com.rykova_e.kts_project.presentation.ui.screen.my_courses.UserCoursesViewModel
import com.rykova_e.kts_project.presentation.ui.screen.profile.UserProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val platformViewModelModule = module {

    viewModel {
        LoginViewModelCommon(
            authUseCase = get(),
            platformAuthService = get(),
            toastChannel = get(named("toast_channel")),
            openAuthPageChannel = get(named("auth_page_channel")),
            authSuccessChannel = get(named("auth_success_channel")),
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
            courseRepositoryLocal = get(),
        )
    }

    viewModel { parameters ->
        CourseDetailViewModel(
            courseId = parameters.get<Long>(),
            getCourseRepositoryCommonImpl = get(),
            singUpOnCourseUseCase = get(),
        )
    }

    viewModel {
        UserCoursesViewModel(
            getUserCoursesUseCase = get(),
            courseRepository = get(),
            userRepository = get()
        )
    }
}