package com.rykova_e.kts_project.data.source.local.data_store

import kotlinx.coroutines.flow.Flow

interface SettingsStorage {
    fun observeFirstOpen(): Flow<Boolean>
    suspend fun setFirstOpen(): Result<Unit>
    suspend fun clearAll(): Result<Unit>
    fun observeAccessToken(): Flow<String>
    suspend fun saveAccessToken(value: String): Result<Unit>
    fun observeRefreshToken(): Flow<String>
    suspend fun saveRefreshToken(value: String): Result<Unit>
}