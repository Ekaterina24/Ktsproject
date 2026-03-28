package com.rykova_e.kts_project.presentation.ui.mapper

import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.CoursesDto
import com.rykova_e.kts_project.domain.model.SearchCoursesDto
import com.rykova_e.kts_project.domain.model.SearchDto
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.SearchCoursesModel
import com.rykova_e.kts_project.presentation.ui.model.SearchModel
import com.rykova_e.kts_project.presentation.ui.model.UserModel
import com.rykova_e.kts_project.presentation.ui.model.CoursesModel

fun CourseDto.toUI(): CourseModel {
    return CourseModel(
        id = this.id,
        title = this.title,
        description = this.description,
        authors = this.authors.map { UserModel(id = it) },
        cover = this.cover,
        rating = this.rating,
        countStudents = this.countStudents,
        duration = this.duration
    )
}

fun CoursesDto.toUI(): CoursesModel {
    return CoursesModel(
        page = this.page,
        hasNext = this.hasNext,
        courses = this.courses.map { it.toUI() }
    )
}

fun SearchCoursesDto.toUI(): SearchCoursesModel {
    return SearchCoursesModel(
        page = this.page,
        hasNext = this.hasNext,
        courseIds = this.searchItems.map { it.toUI() },
        courses = listOf()
    )
}

fun SearchDto.toUI(): SearchModel {
    return SearchModel(
        id = this.id,
        courseId = this.courseId
    )
}