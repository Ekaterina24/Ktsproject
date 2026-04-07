package com.rykova_e.kts_project.data.source

import com.rykova_e.kts_project.data.source.local.db.model.CourseAuthorCrossRef
import com.rykova_e.kts_project.data.source.local.db.model.CourseReviewCrossRef
import com.rykova_e.kts_project.data.source.local.db.repository.CourseRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.ReviewRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.UserRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.remote.CourseRepositoryImpl
import com.rykova_e.kts_project.data.source.remote.UserRepositoryImpl
import com.rykova_e.kts_project.presentation.ui.mapper.toCommonCourseUI
import com.rykova_e.kts_project.presentation.ui.mapper.toDto
import com.rykova_e.kts_project.presentation.ui.mapper.toUI
import com.rykova_e.kts_project.presentation.ui.model.CommonCourses
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.utils.roundToDecimal

class CourseRepositoryCommonImpl {
    private val courseRepository = CourseRepositoryImpl()
    private val courseRepositoryLocal = CourseRepositoryLocalImpl()

    private val userRepository = UserRepositoryImpl()
    private val userRepositoryLocal = UserRepositoryLocalImpl()
    private val reviewRepositoryLocal = ReviewRepositoryLocalImpl()


    suspend fun getCoursesLocalOrNetwork(search: String, page: Int = 1, online: Boolean): CommonCourses {
        val query = search.trim()
        return when {
            !online -> getCoursesLocal(search)
            query.isEmpty() -> {
                val coursesWrapper = courseRepository.getCourses(page = 1).toUI()
                courseRepositoryLocal.saveCourses(coursesWrapper.courses.map { it.toDto() })

                val coursesWithAuthor = getCoursesWithAuthors(coursesWrapper.courses)
                coursesWrapper.copy(courses = coursesWithAuthor)
            }
            else -> {
                val searchWrapper = courseRepository.searchCourses(search, page).toUI()
                val getCourses =
                    courseRepository.getCoursesByIds(searchWrapper.courseIds.map { it.course })
                        .map { it.toUI() }
                val coursesWithAuthor = getCoursesWithAuthors(getCourses)
                searchWrapper.copy(courses = coursesWithAuthor)
            }
        }
    }

    suspend fun loadMoreCourses(search: String, nextPage: Int): CommonCourses {
        return when {
            search.isNotEmpty() -> {
                val coursesWrapper = courseRepository.searchCourses(
                    query = search,
                    page = nextPage
                ).toUI()
                val getCourses =
                    courseRepository.getCoursesByIds(coursesWrapper.courseIds.map { it.course })
                        .map { it.toUI() }
                val coursesWithAuthor = getCoursesWithAuthors(getCourses)
                coursesWrapper.copy(courses = coursesWithAuthor)
            }
            else -> {
                val coursesWrapper = courseRepository.getCourses(page = nextPage).toUI()
                courseRepositoryLocal.saveCourses(coursesWrapper.courses.map { it.toDto() })

                val coursesWithAuthor = getCoursesWithAuthors(coursesWrapper.courses)
                coursesWrapper.copy(courses = coursesWithAuthor)
            }
        }
    }

    private suspend fun getCoursesWithAuthors(courses: List<CourseModel>): List<CourseModel> {
        val authorsId = courses.flatMap { it.authors.map { it.id } }
        val authors = userRepository.getUsersByIds(authorsId)
        userRepositoryLocal.saveUsers(authors)

        // связи авторов
        val authorCrossRefs = courses.flatMap { course ->
            course.authors.map { author ->
                CourseAuthorCrossRef(course.id, author.id)
            }
        }
        courseRepositoryLocal.insertCourseAuthors(authorCrossRefs)

        val authorsUI = authors.map { it.toUI() }
        val authorsMap = authorsUI.associateBy { it.id }

        val ratingIds = courses.map { it.rating?.toLong() ?: 0 }
        val reviews = courseRepository.getReviewsByCourseIds(ratingIds)
        reviewRepositoryLocal.saveReviews(reviews)

        val reviewsMap = reviews.associateBy { it.courseId }

        val coursesWithAuthors = courses.map { course ->
            val courseAuthors = course.authors.mapNotNull { author ->
                authorsMap[author.id]
            }
            course.copy(authors = courseAuthors)
        }

        // связи отзывов
        val reviewCrossRefs = reviews.map { review ->
            CourseReviewCrossRef(review.courseId.toLong(), review.id)
        }
        courseRepositoryLocal.insertCourseReviews(reviewCrossRefs)

        val coursesWithReviews = coursesWithAuthors.map { course ->
            val courseReview = reviewsMap[course.id.toString()]
            course.copy(rating = courseReview?.averageReview?.roundToDecimal() ?: "")
        }

        return coursesWithReviews
    }

    private suspend fun getCoursesLocal(search: String): CommonCourses =
        courseRepositoryLocal.searchCoursesData(search).toCommonCourseUI()
}