package com.rykova_e.kts_project.data.mapper

import com.rykova_e.kts_project.data.source.local.db.model.CourseEntity
import com.rykova_e.kts_project.data.source.local.db.model.CourseWithData
import com.rykova_e.kts_project.data.source.local.db.model.ReviewEntity
import com.rykova_e.kts_project.data.source.remote.model.CourseRemote
import com.rykova_e.kts_project.data.source.remote.model.MetaData
import com.rykova_e.kts_project.data.source.remote.model.ReviewRemote
import com.rykova_e.kts_project.data.source.remote.model.SearchItem
import com.rykova_e.kts_project.data.source.remote.model.SearchWrapper
import com.rykova_e.kts_project.data.source.remote.model.WrapperCourses
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CourseWithDataDto
import com.rykova_e.kts_project.domain.model.MetaDataDto
import com.rykova_e.kts_project.domain.model.ReviewDto
import com.rykova_e.kts_project.domain.model.SearchDto
import com.rykova_e.kts_project.domain.model.WrapperCoursesDto
import com.rykova_e.kts_project.domain.model.WrapperSearchCoursesDto

fun CourseRemote.toDto(): CourseDto {
    return CourseDto(
        id = this.id,
        title = this.title,
        description = this.description,
        authors = this.authors,
        cover = this.cover,
        rating = this.rating,
        countStudents = this.countStudents,
        duration = this.duration
    )
}

fun WrapperCourses.toDto(): WrapperCoursesDto {
    return WrapperCoursesDto(
        meta = this.meta.toDto(),
        courses = this.courses.map { it.toDto() }
    )
}

fun SearchWrapper.toDto(): WrapperSearchCoursesDto {
    return WrapperSearchCoursesDto(
        meta = this.meta.toDto(),
        searchItems = this.searchItems.map { it.toDto() }
    )
}

fun SearchItem.toDto(): SearchDto {
    return SearchDto(
        id = this.id,
        course = this.course
    )
}

fun MetaData.toDto(): MetaDataDto {
    return MetaDataDto(
        page = this.page,
        has_next = this.has_next,
        has_previous = this.has_previous
    )
}

fun ReviewRemote.toDto(): ReviewDto {
    return ReviewDto(
        id = this.id,
        courseId = this.courseId,
        averageReview = this.averageReview
    )
}

fun CourseEntity.toDto(): CourseDto {
    return CourseDto(
        id = this.courseId,
        title = this.title,
        description = this.description,
        authors = listOf(),
        cover = this.cover,
        rating = this.rating,
        countStudents = this.countStudents,
        duration = this.duration
    )
}

fun CourseDto.toEntity(): CourseEntity {
    return CourseEntity(
        courseId = this.id,
        title = this.title,
        description = this.description,
        cover = this.cover,
        rating = this.rating,
        countStudents = this.countStudents,
        duration = this.duration
    )
}

fun ReviewEntity.toDto(): ReviewDto {
    return ReviewDto(
        id = this.reviewId,
        courseId = this.courseId.toString(),
        averageReview = this.averageReview
    )
}

fun ReviewDto.toEntity(): ReviewEntity {
    return ReviewEntity(
        reviewId = this.id,
        courseId = this.courseId.toLong(),
        averageReview = this.averageReview,
        count = 0
    )
}

fun CourseWithData.toDto(): CourseWithDataDto {
    return CourseWithDataDto(
        course = this.course.toDto(),
        authors = this.authors.map { it.toDto() },
        review = this.review?.toDto()
    )
}