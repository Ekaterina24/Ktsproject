package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ReviewWrapper(
    @SerialName("course-review-summaries")
    val reviews: List<ReviewRemote>
)