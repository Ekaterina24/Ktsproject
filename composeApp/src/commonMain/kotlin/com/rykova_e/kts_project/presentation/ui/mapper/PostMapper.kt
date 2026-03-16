package com.rykova_e.kts_project.presentation.ui.mapper

import com.rykova_e.kts_project.domain.model.PostDto
import com.rykova_e.kts_project.presentation.ui.screen.main.PostModel

fun PostModel.toDto(): PostDto {
    return PostDto(
        id = this.id,
        title = this.title,
        time = this.time,
        description = this.description,
        image = this.image
    )
}

fun PostDto.toUI(): PostModel {
    return PostModel(
        id = this.id,
        title = this.title,
        time = this.time,
        description = this.description,
        image = this.image
    )
}