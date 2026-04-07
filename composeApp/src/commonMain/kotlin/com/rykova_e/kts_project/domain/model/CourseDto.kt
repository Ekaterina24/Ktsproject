package com.rykova_e.kts_project.domain.model

data class CourseDto(
    val id: Long,
    val title: String,
    val description: String,
    val authors: List<Long>,
    val cover: String,
    val rating: String?,
    val countStudents: Long,
    val duration: Long?,
    val price: String?,
    val isPaid: Boolean,
    val isRecord: Boolean,
    val progress: String?,
    val score: Int,
    val cost: Int
)
