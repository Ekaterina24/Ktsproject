package com.rykova_e.kts_project.presentation.ui.mapper

import com.rykova_e.kts_project.domain.model.CourseDto
import com.rykova_e.kts_project.domain.model.MetaDataDto
import com.rykova_e.kts_project.domain.model.SearchDto
import com.rykova_e.kts_project.domain.model.WrapperCoursesDto
import com.rykova_e.kts_project.domain.model.WrapperSearchCoursesDto
import com.rykova_e.kts_project.presentation.ui.model.CourseModel
import com.rykova_e.kts_project.presentation.ui.model.MetaDataModel
import com.rykova_e.kts_project.presentation.ui.model.SearchModel
import com.rykova_e.kts_project.presentation.ui.model.UserModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperCoursesModel
import com.rykova_e.kts_project.presentation.ui.model.WrapperSearchCoursesModel

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

fun WrapperCoursesDto.toUI(): WrapperCoursesModel {
    return WrapperCoursesModel(
        metaData = this.meta.toUI(),
        courses = this.courses.map { it.toUI() }
    )
}

fun WrapperSearchCoursesDto.toUI(): WrapperSearchCoursesModel {
    return WrapperSearchCoursesModel(
        metaData = this.meta.toUI(),
        courseIds = this.searchItems.map { it.toUI() },
        courses = listOf()
    )
}

fun MetaDataDto.toUI(): MetaDataModel {
    return MetaDataModel(
        page = this.page,
        has_next = this.has_next,
        has_previous = this.has_previous
    )
}

fun SearchDto.toUI(): SearchModel {
    return SearchModel(
        id = this.id,
        course = this.course
    )
}