package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.data.auth.model.TokensModel

interface PlatformAuthService {
    fun getAuthorizationRequestIntent(
        authRequest: AuthRequest
    ): PlatformIntent
}

expect class PlatformIntent
expect class AuthRequest
expect class EndSessionRequest
expect class TokenRequest

interface AuthRepository {
    fun corruptAccessToken()
    fun logout()
    fun getAuthRequest(): AuthRequest
    fun getEndSessionRequest(): EndSessionRequest
    suspend fun performTokenRequest(
        authService: PlatformAuthService,
        tokenRequest: TokenRequest
    )
    suspend fun exchangeCodeForTokens(
        code: String,
        authService: PlatformAuthService
    ): TokensModel
}