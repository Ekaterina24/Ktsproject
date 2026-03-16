package com.rykova_e.kts_project.domain.repository

class LoginRepository {

    fun login(username: String, password: String): Result<Unit> {
        return when {
            username.isEmpty() -> Result.failure(IllegalArgumentException("Введите имя"))
            password.isEmpty() -> Result.failure(IllegalArgumentException("Введите пароль"))
            password.length < 8 -> Result.failure(IllegalArgumentException("Длина пароля >= 8 символов"))
            else -> Result.success(Unit)
        }
    }
}