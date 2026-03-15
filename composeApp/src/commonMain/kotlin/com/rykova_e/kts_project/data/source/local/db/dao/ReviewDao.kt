package com.rykova_e.kts_project.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rykova_e.kts_project.data.source.local.db.model.ReviewEntity

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReviews(reviews: List<ReviewEntity>)
}