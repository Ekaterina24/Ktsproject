package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.mapper.toDto
import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.model.UserProfileDto
import com.rykova_e.kts_project.domain.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class UserRepositoryImpl: UserRepository {

    private val apiService = ApiService(Networking.httpClient)
    override suspend fun getUsersByIds(ids: List<Long>): List<UserDto> {
        return coroutineScope {
            val authors = ids.map { id ->
                async {
                    getUser(id)
                }
            }
            authors.awaitAll()
        }
    }

    override suspend fun getUser(id: Long): UserDto {
        return apiService.getUser(id).users.first().toDto()
    }

    override suspend fun getUserProfile(): UserProfileDto {
        return apiService.getUserProfile().users.first().toDto()
    }


}