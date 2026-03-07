package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
class CourseWrapper(
    val courses: List<CourseRemote>
)