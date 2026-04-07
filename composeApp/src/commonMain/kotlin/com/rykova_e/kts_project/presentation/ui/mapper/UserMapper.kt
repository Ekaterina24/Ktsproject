package com.rykova_e.kts_project.presentation.ui.mapper

import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.model.UserProfileDto
import com.rykova_e.kts_project.presentation.ui.model.UserModel
import com.rykova_e.kts_project.presentation.ui.model.UserProfileModel

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

fun UserProfileDto.toUI(): UserProfileModel {
    return UserProfileModel(
        name = this.name,
        surname = this.surname,
        avatar = this.avatar
    )
}