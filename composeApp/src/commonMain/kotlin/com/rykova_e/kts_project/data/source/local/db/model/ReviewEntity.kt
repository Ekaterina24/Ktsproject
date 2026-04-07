package com.rykova_e.kts_project.data.source.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "review",
    indices = [Index("review_id", unique = true)]
)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "review_id")
    val reviewId: Long,

    @ColumnInfo(name = "course_id")
    val courseId: Long,

    @ColumnInfo(name = "average")
    val averageReview: String,

    @ColumnInfo(name = "count")
    val count: Long,
)