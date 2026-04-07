package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("first_name")
    val name: String,
    @SerialName("last_name")
    val surname: String,
    @SerialName("avatar")
    val avatar: String,
)
