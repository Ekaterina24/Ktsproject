package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WrapperUser(
    val users: List<UserRemote>
)