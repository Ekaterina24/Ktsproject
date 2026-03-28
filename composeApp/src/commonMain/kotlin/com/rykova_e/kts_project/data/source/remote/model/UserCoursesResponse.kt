package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserCoursesResponse(
    @SerialName("user-courses")
    val courses: List<UserCoursesRemote>
)

@Serializable
class UserCoursesRemote(
    @SerialName("course")
    val courseId: Long,
    @SerialName("is_favorite")
    val isFavorite: Boolean,
)