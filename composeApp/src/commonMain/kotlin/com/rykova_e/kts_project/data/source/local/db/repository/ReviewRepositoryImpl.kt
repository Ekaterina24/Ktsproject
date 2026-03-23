package com.rykova_e.kts_project.data.source.local.db.repository

import com.rykova_e.kts_project.data.mapper.toEntity
import com.rykova_e.kts_project.data.source.local.db.dao.ReviewDao
import com.rykova_e.kts_project.domain.model.ReviewDto
import com.rykova_e.kts_project.domain.repository.local.ReviewRepositoryLocal
import com.rykova_e.kts_project.utils.suspendRunCatching

class ReviewRepositoryLocalImpl(
    private val reviewDao: ReviewDao
): ReviewRepositoryLocal {
    override suspend fun saveReviews(reviews: List<ReviewDto>): Result<Unit> = suspendRunCatching {
        reviewDao.saveReviews(reviews.map { it.toEntity() })
    }
}