package com.rykova_e.kts_project.data.auth.model

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)