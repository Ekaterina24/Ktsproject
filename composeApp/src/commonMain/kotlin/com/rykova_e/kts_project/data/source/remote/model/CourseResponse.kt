package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CourseResponse(
    @SerialName("courses")
    val courses: List<CourseRemote>
)