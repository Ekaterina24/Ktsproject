package com.rykova_e.kts_project.data

import androidx.core.net.toUri
import com.rykova_e.kts_project.data.auth.model.TokensModel
import kotlinx.coroutines.suspendCancellableCoroutine
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import java.util.UUID

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        AuthConfig.AUTH_URI.toUri(),
        AuthConfig.TOKEN_URI.toUri(),
        null, // registration endpoint
        AuthConfig.END_SESSION_URI.toUri()
    )

    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = AuthConfig.CALLBACK_URL.toUri()

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    private fun generateRandomState(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    fun getEndSessionRequest(): EndSessionRequest {
        return EndSessionRequest.Builder(serviceConfiguration)
            .setPostLogoutRedirectUri(AuthConfig.LOGOUT_CALLBACK_URL.toUri())
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(AuthConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun exchangeCodeForTokens(
        code: String,
        authService: AuthorizationService
    ): TokensModel {
        // Создаём TokenRequest вручную для authorization_code
        val tokenRequest = TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType("authorization_code")
            .setAuthorizationCode(code)
            .setRedirectUri(AuthConfig.CALLBACK_URL.toUri())
            .setScopes(AuthConfig.SCOPE)
            .build()

        return performTokenRequestSuspend(authService, tokenRequest)
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): TokensModel {
        return suspendCancellableCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication()
            ) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }

                    else -> error("unreachable")
                }
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }

    object AuthConfig {
        const val AUTH_URI = "https://stepik.org/oauth2/authorize"
        const val CALLBACK_URL = "https://88o4lz-46-188-126-136.ru.tuna.am/callback"
        const val TOKEN_URI = "https://stepik.org/oauth2/token/"

        const val END_SESSION_URI = "https://oauth.yandex.ru/logout"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "read write"

        const val CLIENT_ID = "8VutJ5rSgR49Q7TFjBsIfQHWGcPou7WH86VZfpkY"
        const val CLIENT_SECRET = "diI50DbNLJCBuZzy8Ne4z0bWzSe9uBnkaZNnyA7vEcOEJydeddr3kXXz78fdFH2AT45cOMu8dOrCSrF3hRYoev16cbsDqunWZadCPMLYUdLkCAEP5sixckoqhSQKjbTx"

        const val LOGOUT_CALLBACK_URL = "com.example.rusycosy://oauth/logout_callback"

    }
}