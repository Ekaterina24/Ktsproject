package com.rykova_e.kts_project.domain.repository.local

import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CourseWithDataDto
import kotlinx.coroutines.flow.Flow

interface CourseRepositoryLocal {

    suspend fun saveCourses(courses: List<CourseDto>): Result<Unit>
    fun searchCoursesData(search: String): Result<Flow<List<CourseWithDataDto>>>
    suspend fun insertCourseAuthors(crossRefs: List<CourseAuthorCrossRef>)

    suspend fun insertCourseReviews(crossRefs: List<CourseReviewCrossRef>)
}