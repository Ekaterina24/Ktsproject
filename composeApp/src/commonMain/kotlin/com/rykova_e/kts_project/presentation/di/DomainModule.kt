package com.rykova_e.kts_project.presentation.di

import com.rykova_e.kts_project.domain.use_case.AuthUseCase
import com.rykova_e.kts_project.domain.use_case.GetUserCoursesUseCase
import com.rykova_e.kts_project.domain.use_case.SingUpOnCourseUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { AuthUseCase(get()) }
    factory { SingUpOnCourseUseCase(get()) }
    factory { GetUserCoursesUseCase(get()) }
}