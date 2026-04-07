package com.rykova_e.kts_project.data.source.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "course",
    indices = [Index("course_id", unique = true)]
)
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "course_id")
    val courseId: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "cover")
    val cover: String,

    @ColumnInfo(name = "rating")
    val rating: String? = null,

    @ColumnInfo(name = "count_students")
    val countStudents: Long,

    @ColumnInfo(name = "duration")
    val duration: Long?,

    @ColumnInfo(name = "price")
    val price: String? = null,

    @ColumnInfo(name = "is_free")
    val isPaid: Boolean = false,

    @ColumnInfo(name = "is_record")
    val isRecord: Boolean = false,

    @ColumnInfo(name = "progress")
    val progress: String? = null,

    @ColumnInfo(name = "score")
    val score: Int = 0,

    @ColumnInfo(name = "cost")
    val cost: Int = 0,
)