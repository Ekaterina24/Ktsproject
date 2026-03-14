package com.rykova_e.kts_project.data.mapper

import com.rykova_e.kts_project.data.source.remote.model.CourseRemote
import com.rykova_e.kts_project.data.source.remote.model.MetaData
import com.rykova_e.kts_project.data.source.remote.model.ReviewRemote
import com.rykova_e.kts_project.data.source.remote.model.SearchItem
import com.rykova_e.kts_project.data.source.remote.model.SearchWrapper
import com.rykova_e.kts_project.data.source.remote.model.WrapperCourses
import com.rykova_e.kts_project.domain.model.CourseDto
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