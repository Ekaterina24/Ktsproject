package com.rykova_e.kts_project.presentation.ui.mapper

import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.presentation.ui.model.UserModel

fun UserModel.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name
    )
}

fun UserDto.toUI(): UserModel {
    return UserModel(
        id = this.id,
        name = this.name
    )
}