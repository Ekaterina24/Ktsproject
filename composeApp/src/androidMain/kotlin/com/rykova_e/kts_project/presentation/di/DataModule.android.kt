package com.rykova_e.kts_project.presentation.di

import android.content.Intent
import com.rykova_e.kts_project.data.AndroidChannel
import com.rykova_e.kts_project.data.AndroidPlatformAuthService
import com.rykova_e.kts_project.data.repository.AuthRepositoryImpl
import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.domain.repository.AuthRepository
import com.rykova_e.kts_project.domain.repository.PlatformAuthService
import com.rykova_e.kts_project.domain.repository.PlatformIntent
import com.rykova_e.kts_project.presentation.ui.screen.login.PlatformChannel
import kotlinx.coroutines.channels.Channel
import net.openid.appauth.AuthorizationService
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun dataModule() = module {

    factory<AuthRepository> { AuthRepositoryImpl() }
    factory<PlatformAuthService> { AndroidPlatformAuthService(get()) }
    factory<AuthorizationService> { AuthorizationService(get()) }

    // Toast
    factory(named("toast_channel")) {
        Channel<String>(Channel.BUFFERED)
    }

    factory<PlatformChannel<String>>(named("toast_channel")) {
        AndroidChannel(get(named("toast_channel")))
    }

    // Open Auth Page
    factory(named("auth_page_channel")) {
        Channel<Intent>(Channel.BUFFERED)
    }

    factory<PlatformChannel<PlatformIntent>>(named("auth_page_channel")) {
        AndroidChannel(get(named("auth_page_channel")))
    }

    // Auth Success
    factory(named("auth_success_channel")) {
        Channel<Unit>(Channel.BUFFERED)
    }

    factory<PlatformChannel<Unit>>(named("auth_success_channel")) {
        AndroidChannel(get(named("auth_success_channel")))
    }

    single<SettingsStorage> { DataStoreSettingsStorage(get()) }
}