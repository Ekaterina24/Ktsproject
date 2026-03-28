package com.rykova_e.kts_project.presentation.ui.model

data class CourseModel(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val authors: List<UserModel> = listOf(),
    val cover: String = "",
    val rating: String = "",
    val countStudents: Long = 0L,
    val duration: Long? = null,
)