package com.rykova_e.kts_project.presentation.ui.model

data class WrapperSearchCoursesModel(
    override val metaData: MetaDataModel,
    val courseIds: List<SearchModel>,
    override val courses: List<CourseModel>
): CommonCourses(metaData, courses)
