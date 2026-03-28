package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("first_name")
    val name: String,
)
