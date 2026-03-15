package com.rykova_e.kts_project.data.source.local.db.repository

import com.rykova_e.kts_project.data.mapper.toEntity
import com.rykova_e.kts_project.data.source.local.db.DatabaseProvider
import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.repository.local.UserRepositoryLocal
import com.rykova_e.kts_project.utils.suspendRunCatching

class UserRepositoryLocalImpl: UserRepositoryLocal {

    private val userDao = DatabaseProvider.instance.userDao()
    override suspend fun saveUsers(users: List<UserDto>): Result<Unit> = suspendRunCatching {
        userDao.saveUsers(users.map { it.toEntity() })
    }
}