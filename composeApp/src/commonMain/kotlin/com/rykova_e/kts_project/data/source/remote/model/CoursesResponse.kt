package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    @SerialName("meta")
    val meta: MetaDataRemote,
    @SerialName("courses")
    val courses: List<CourseRemote>
)

@Serializable
data class MetaDataRemote(
    @SerialName("page")
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    val hasPrevious: Boolean
)
