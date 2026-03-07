package com.rykova_e.kts_project.presentation.ui.model

data class WrapperSearchCoursesModel(
    val metaData: MetaDataModel,
    val courseIds: List<SearchModel>,
    val courses: List<CourseModel>
)
