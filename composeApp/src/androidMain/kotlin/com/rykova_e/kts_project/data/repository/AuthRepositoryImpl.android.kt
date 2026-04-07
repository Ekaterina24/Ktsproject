package com.rykova_e.kts_project.data.repository

import android.util.Log
import com.rykova_e.kts_project.data.AndroidPlatformAuthService
import com.rykova_e.kts_project.data.AppAuth
import com.rykova_e.kts_project.data.auth.TokenStorage
import com.rykova_e.kts_project.data.auth.model.TokensModel
import com.rykova_e.kts_project.domain.repository.AuthRepository
import com.rykova_e.kts_project.domain.repository.AuthRequest
import com.rykova_e.kts_project.domain.repository.EndSessionRequest
import com.rykova_e.kts_project.domain.repository.PlatformAuthService
import com.rykova_e.kts_project.domain.repository.TokenRequest


class AuthRepositoryImpl: AuthRepository {
    override fun corruptAccessToken() {
        TokenStorage.accessToken = "fake token"
    }

    override fun logout() {
        TokenStorage.accessToken = null
        TokenStorage.refreshToken = null
        TokenStorage.idToken = null
    }

    override fun getAuthRequest(): AuthRequest {
        return AppAuth.getAuthRequest()
    }

    override fun getEndSessionRequest(): EndSessionRequest {
        return AppAuth.getEndSessionRequest()
    }

    override suspend fun performTokenRequest(
        authService: PlatformAuthService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(
            authService = (authService as AndroidPlatformAuthService).service,
            tokenRequest = tokenRequest
        )
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        TokenStorage.idToken = tokens.idToken
        Log.d("AUTH", "5. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\n")
    }

    override suspend fun exchangeCodeForTokens(
        code: String,
        authService: PlatformAuthService,
    ): TokensModel {
        return AppAuth.exchangeCodeForTokens(code, (authService as AndroidPlatformAuthService).service)
    }
}