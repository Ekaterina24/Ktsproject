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

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCourses(courses: List<CourseEntity>)

    @Transaction
    @Query("SELECT * FROM course WHERE :search = '' " +
            "OR LOWER(title) LIKE LOWER('%' || :search || '%')")
    suspend fun searchCoursesData(search: String): List<CourseWithData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseAuthors(crossRefs: List<CourseAuthorCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourseReviews(crossRefs: List<CourseReviewCrossRef>)

    @Query("DELETE FROM CourseAuthorCrossRef")
    suspend fun deleteAuthorCrossRefs()

    @Query("DELETE FROM CourseReviewCrossRef")
    suspend fun deleteReviewCrossRefs()

    @Query("DELETE FROM course")
    suspend fun deleteCourses()

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

    @Query("DELETE FROM review")
    suspend fun deleteReviews()

    @Transaction
    suspend fun clearAllData() {
        deleteAuthorCrossRefs()
        deleteReviewCrossRefs()
        deleteCourses()
        deleteUsers()
        deleteReviews()
    }
}