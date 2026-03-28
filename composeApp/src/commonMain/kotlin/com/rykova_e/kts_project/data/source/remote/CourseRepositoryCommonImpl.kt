package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CommonCoursesData
import com.rykova_e.kts_project.presentation.ui.model.CourseModel

class CourseRepositoryCommonImpl {
    private val courseRepository = CourseRepositoryImpl()

    private val userRepository = UserRepositoryImpl()


    suspend fun getCoursesNetwork(search: String, page: Int = 1): Result<CommonCoursesData> {
        return runCatching { loadCourses(search, page) }
    }

    suspend fun loadMoreCourses(search: String, nextPage: Int): Result<CommonCoursesData> {
        return runCatching { loadCourses(search, nextPage) }
    }

    private suspend fun loadCourses(search: String, page: Int): CommonCoursesData {
        val query = search.trim()

        val data = if (query.isEmpty()) {
            val coursesModel = courseRepository.getCourses(page = page).getOrThrow().toUI()
            val coursesWithAuthors = getCoursesWithAuthors(coursesModel.courses)
            coursesModel.copy(courses = coursesWithAuthors)
        } else {
            val searchCoursesModel = courseRepository.searchCourses(query, page).getOrThrow().toUI()
            val courses = courseRepository
                .getCoursesByIds(searchCoursesModel.courseIds.map { it.courseId })
                .getOrThrow()
                .map { it.toUI() }

            val coursesWithAuthors = getCoursesWithAuthors(courses)
            searchCoursesModel.copy(courses = coursesWithAuthors)
        }

        return data
    }

    private suspend fun getCoursesWithAuthors(courses: List<CourseModel>): List<CourseModel> {
        val authorsId = courses.flatMap { it.authors.map { it.id } }
        val authors = userRepository.getUsersByIds(authorsId).getOrThrow().map { it.toUI() }
        val authorsMap = authors.associateBy { it.id }

        val coursesWithAuthors = courses.map { course ->
            val courseAuthors = course.authors.mapNotNull { author ->
                authorsMap[author.id]
            }
            course.copy(authors = courseAuthors)
        }

        return coursesWithAuthors
    }
}