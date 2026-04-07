package com.rykova_e.kts_project.data

import androidx.browser.customtabs.CustomTabsIntent
import com.rykova_e.kts_project.domain.repository.AuthRequest
import com.rykova_e.kts_project.domain.repository.PlatformAuthService
import com.rykova_e.kts_project.domain.repository.PlatformIntent
import com.rykova_e.kts_project.presentation.ui.screen.login.PlatformChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import net.openid.appauth.AuthorizationService

class AndroidPlatformAuthService(
    val service: AuthorizationService
) : PlatformAuthService {
    override fun getAuthorizationRequestIntent(
        authRequest: AuthRequest
    ): PlatformIntent {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val intent = service.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        return PlatformIntent(intent)
    }
}

class AndroidChannel<T>(private val channel: Channel<T>) : PlatformChannel<T> {
    override fun send(data: T) = channel.trySendBlocking(data)
    override fun receiveAsFlow(): Flow<T> = channel.receiveAsFlow()
}