package com.rykova_e.kts_project.data.source.local.db.repository

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.data.mapper.toEntity
import com.rykova_e.kts_project.data.source.local.db.DatabaseProvider
import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CourseWithDataDto
import com.rykova_e.kts_project.domain.repository.local.CourseRepositoryLocal
import com.rykova_e.kts_project.utils.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepositoryLocalImpl: CourseRepositoryLocal {

    private val courseDao = DatabaseProvider.instance.courseDao()
    override suspend fun saveCourses(courses: List<CourseDto>): Result<Unit> = suspendRunCatching {
        courseDao.saveCourses(courses.map { it.toEntity() })
    }

    override fun searchCoursesData(search: String): Result<Flow<List<CourseWithDataDto>>> = suspendRunCatching {
        courseDao.searchCoursesData(search).map { it.map { it.toDto() } }
    }

    override suspend fun insertCourseAuthors(crossRefs: List<CourseAuthorCrossRef>) {
        courseDao.insertCourseAuthors(crossRefs)
    }

    override suspend fun insertCourseReviews(crossRefs: List<CourseReviewCrossRef>) {
        courseDao.insertCourseReviews(crossRefs)
    }
}