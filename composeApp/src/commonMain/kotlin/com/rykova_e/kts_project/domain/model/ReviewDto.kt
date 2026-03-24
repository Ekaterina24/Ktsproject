package com.rykova_e.kts_project.domain.model

data class ReviewDto(
    val id: Long,
    val courseId: String,
    val averageReview: String,
    val count: Int
)
