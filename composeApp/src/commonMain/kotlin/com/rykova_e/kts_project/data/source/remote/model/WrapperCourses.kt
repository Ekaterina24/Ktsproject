package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WrapperCourses(
    val meta: MetaData,
    val courses: List<CourseRemote>
)

@Serializable
data class MetaData(
    val page: Int,
    val has_next: Boolean,
    val has_previous: Boolean
)
