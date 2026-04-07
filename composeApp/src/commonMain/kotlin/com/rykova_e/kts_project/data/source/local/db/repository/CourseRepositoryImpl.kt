package com.rykova_e.kts_project.data.source.local.db.repository

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.data.mapper.toEntity
import com.rykova_e.kts_project.data.source.local.db.dao.CourseDao
import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CourseWithDataDto
import com.rykova_e.kts_project.domain.repository.local.CourseRepositoryLocal
import com.rykova_e.kts_project.utils.suspendRunCatching

class CourseRepositoryLocalImpl(
    private val courseDao: CourseDao
): CourseRepositoryLocal {
    override suspend fun saveCourses(courses: List<CourseDto>): Result<Unit> = suspendRunCatching {
        courseDao.saveCourses(courses.map { it.toEntity() })
    }

    override suspend fun searchCoursesData(search: String): List<CourseWithDataDto> =
        courseDao.searchCoursesData(search).map { it.toDto() }

    override suspend fun insertCourseAuthors(crossRefs: List<CourseAuthorCrossRef>) {
        courseDao.insertCourseAuthors(crossRefs)
    }

    override suspend fun insertCourseReviews(crossRefs: List<CourseReviewCrossRef>) {
        courseDao.insertCourseReviews(crossRefs)
    }

    override suspend fun clearAllData() {
        courseDao.clearAllData()
    }
}