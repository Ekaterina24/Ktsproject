package com.rykova_e.kts_project.presentation.ui.screen.main

data class PostModel(
    val id: Long,
    val title: String,
    val time: String,
    val description: String,
    val image: String = "https://static.vecteezy.com/system/resources/previews/068/842/002/non_2x/vk-logo-icon-vk-app-transparent-background-free-png.png"
)
