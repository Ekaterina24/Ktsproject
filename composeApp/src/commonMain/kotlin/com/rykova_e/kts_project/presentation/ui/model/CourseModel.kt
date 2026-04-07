package com.rykova_e.kts_project.presentation.ui.model

data class CourseModel(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val authors: List<UserModel> = listOf(),
    val cover: String = "",
    val rating: String? = null,
    val countStudents: Long = 0L,
    val duration: Long? = null,
    val countReviews: Int? = null,
    val price: String? = null,
    val isPaid: Boolean = true,
    val isRecord: Boolean = false,
    val percentProgress: String? = null,
    val score: Int = 0,
    val cost: Int = 0
)