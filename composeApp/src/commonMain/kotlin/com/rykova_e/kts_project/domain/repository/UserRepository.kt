package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.domain.model.UserDto

interface UserRepository {

    suspend fun getUsersByIds(ids: List<Long>): List<UserDto>
    suspend fun getUser(id: Long): UserDto
}