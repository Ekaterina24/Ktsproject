package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProgressCourseResponse(
    @SerialName("progresses")
    val progresses: List<ProgressCourseRemote>
)

@Serializable
class ProgressCourseRemote(
    @SerialName("id")
    val id: String,
    @SerialName("score")
    val score: Float,
    @SerialName("cost")
    val cost: Int,
)