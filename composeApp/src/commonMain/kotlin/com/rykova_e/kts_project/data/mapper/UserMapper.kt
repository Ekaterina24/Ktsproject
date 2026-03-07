package com.rykova_e.kts_project.data.mapper

import com.rykova_e.kts_project.data.source.remote.model.UserRemote
import com.rykova_e.kts_project.domain.model.UserDto

fun UserRemote.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name
    )
}