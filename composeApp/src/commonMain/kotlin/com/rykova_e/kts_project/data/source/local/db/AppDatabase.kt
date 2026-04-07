package com.rykova_e.kts_project.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rykova_e.kts_project.data.source.local.db.dao.CourseDao
import com.rykova_e.kts_project.data.source.local.db.dao.ReviewDao
import com.rykova_e.kts_project.data.source.local.db.dao.UserDao
import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseEntity
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.ReviewEntity
import com.rykova_e.kts_project.data.source.local.db.model.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CourseEntity::class,
        ReviewEntity::class,
        CourseAuthorCrossRef::class,
        CourseReviewCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao
}