package com.rykova_e.kts_project.presentation.di

import com.rykova_e.kts_project.domain.use_case.AuthUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<AuthUseCase> { AuthUseCase(get()) }
}