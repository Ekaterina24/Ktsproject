package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CurrentUserWrapper(
    @SerialName("users")
    val users: List<UserProfileRemote>
)