package com.rykova_e.kts_project.data.mapper

import com.rykova_e.kts_project.data.source.remote.model.CourseRemote
import com.rykova_e.kts_project.data.source.remote.model.CoursesResponse
import com.rykova_e.kts_project.data.source.remote.model.SearchRemote
import com.rykova_e.kts_project.data.source.remote.model.SearchResponse
import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CoursesDto
import com.rykova_e.kts_project.domain.model.SearchCoursesDto
import com.rykova_e.kts_project.domain.model.SearchDto

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

fun CoursesResponse.toDto(): CoursesDto {
    return CoursesDto(
        page = this.meta.page,
        hasNext = this.meta.hasNext,
        courses = this.courses.map { it.toDto() },
    )
}

fun SearchResponse.toDto(): SearchCoursesDto {
    return SearchCoursesDto(
        page = this.meta.page,
        hasNext = this.meta.hasNext,
        searchItems = this.searchItems.map { it.toDto() },
    )
}

fun SearchRemote.toDto(): SearchDto {
    return SearchDto(
        id = this.id,
        courseId = this.courseId
    )
}