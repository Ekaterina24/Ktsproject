package com.rykova_e.kts_project.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseEntity
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCourses(courses: List<CourseEntity>)

    @Transaction
    @Query("SELECT * FROM course WHERE :search = '' " +
            "OR LOWER(title) LIKE LOWER('%' || :search || '%')")
    fun searchCoursesData(search: String): Flow<List<CourseWithData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseAuthors(crossRefs: List<CourseAuthorCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseReviews(crossRefs: List<CourseReviewCrossRef>)
}