package com.rykova_e.kts_project.presentation.ui.model

data class WrapperCoursesModel(
    override val metaData: MetaDataModel,
    override val courses: List<CourseModel>
): CommonCourses(metaData, courses)

open class CommonCourses(
    open val metaData: MetaDataModel,
    open val courses: List<CourseModel>
)

