package com.rykova_e.kts_project.domain.use_case

import com.rykova_e.kts_project.data.auth.model.TokensModel
import com.rykova_e.kts_project.domain.repository.AuthRepository
import com.rykova_e.kts_project.domain.repository.AuthRequest
import com.rykova_e.kts_project.domain.repository.EndSessionRequest
import com.rykova_e.kts_project.domain.repository.PlatformAuthService
import com.rykova_e.kts_project.domain.repository.TokenRequest

class AuthUseCase(
    private val repository: AuthRepository
) {

    fun corruptAccessToken() = repository.corruptAccessToken()
    fun logout() = repository.logout()

    fun getAuthRequest(): AuthRequest = repository.getAuthRequest()
    fun getEndSessionRequest(): EndSessionRequest = repository.getEndSessionRequest()

    suspend fun performTokenRequest(
        authService: PlatformAuthService,
        tokenRequest: TokenRequest
    ) {
        repository.performTokenRequest(authService, tokenRequest)
    }

    suspend fun exchangeCodeForTokens(
        authService: PlatformAuthService,
        code: String
    ): TokensModel {
        return repository.exchangeCodeForTokens(code, authService)
    }
}