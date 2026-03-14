package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("course")
    val courseId: String,
    @SerialName("average")
    val averageReview: String,
    @SerialName("count")
    val count: Long,
    @SerialName("distribution")
    val distribution: List<Int>
)
