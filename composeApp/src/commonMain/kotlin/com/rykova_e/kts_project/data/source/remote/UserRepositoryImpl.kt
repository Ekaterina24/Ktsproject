package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.repository.UserRepository

class UserRepositoryImpl: UserRepository {

    private val apiService = ApiService(Networking.httpClient)
    override suspend fun getUsersByIds(ids: List<Long>): Result<List<UserDto>> {
        return runCatching {
            apiService.getUsersByIds(ids).users.map { it.toDto() }
        }
    }

    override suspend fun getUser(id: Long): Result<UserDto> {
        return runCatching {
            apiService.getUserById(id).users.first().toDto()
        }
    }
}