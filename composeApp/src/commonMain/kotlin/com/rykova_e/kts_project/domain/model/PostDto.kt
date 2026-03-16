package com.rykova_e.kts_project.domain.model

data class PostDto(
    val id: Long,
    val title: String,
    val time: String,
    val description: String,
    val image: String
)
