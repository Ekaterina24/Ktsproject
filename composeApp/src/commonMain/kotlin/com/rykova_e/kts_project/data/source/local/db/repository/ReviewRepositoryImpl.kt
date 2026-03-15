package com.rykova_e.kts_project.data.source.local.db.repository

import com.rykova_e.kts_project.data.mapper.toEntity
import com.rykova_e.kts_project.data.source.local.db.DatabaseProvider
import com.rykova_e.kts_project.domain.model.ReviewDto
import com.rykova_e.kts_project.domain.repository.local.ReviewRepositoryLocal
import com.rykova_e.kts_project.utils.suspendRunCatching

class ReviewRepositoryLocalImpl: ReviewRepositoryLocal {

    private val reviewDao = DatabaseProvider.instance.reviewDao()
    override suspend fun saveReviews(reviews: List<ReviewDto>): Result<Unit> = suspendRunCatching {
        reviewDao.saveReviews(reviews.map { it.toEntity() })
    }
}