package com.rykova_e.kts_project.data.mapper

import com.rykova_e.kts_project.data.source.local.db.model.UserEntity
import com.rykova_e.kts_project.data.source.remote.model.UserProfileRemote
import com.rykova_e.kts_project.data.source.remote.model.UserRemote
import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.model.UserProfileDto

fun UserRemote.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name
    )
}

fun UserEntity.toDto(): UserDto {
    return UserDto(
        id = this.userId,
        name = this.name
    )
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        userId = this.id,
        name = this.name
    )
}

fun UserProfileRemote.toDto(): UserProfileDto {
    return UserProfileDto(
        name = this.name,
        surname = this.surname,
        avatar = this.avatar
    )
}