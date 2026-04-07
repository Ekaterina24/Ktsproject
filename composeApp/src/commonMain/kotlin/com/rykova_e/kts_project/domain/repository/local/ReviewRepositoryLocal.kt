package com.rykova_e.kts_project.domain.repository.local

import com.rykova_e.kts_project.domain.model.ReviewDto

interface ReviewRepositoryLocal {

    suspend fun saveReviews(reviews: List<ReviewDto>): Result<Unit>
}