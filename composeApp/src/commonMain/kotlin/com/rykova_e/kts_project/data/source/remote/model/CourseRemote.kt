package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("authors")
    val authors: List<Long>,
    @SerialName("cover")
    val cover: String,
    @SerialName("review_summary")
    val rating: String,
    @SerialName("learners_count")
    val countStudents: Long,
    @SerialName("time_to_complete")
    val duration: Long?,
)
