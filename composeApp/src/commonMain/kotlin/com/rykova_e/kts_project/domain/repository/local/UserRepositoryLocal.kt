package com.rykova_e.kts_project.domain.repository.local

import com.rykova_e.kts_project.domain.model.UserDto

interface UserRepositoryLocal {

    suspend fun saveUsers(users: List<UserDto>): Result<Unit>
}