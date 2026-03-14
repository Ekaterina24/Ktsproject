package com.rykova_e.kts_project.domain.model

data class TokensDto(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)