package com.rykova_e.kts_project.data.source.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.rykova_e.kts_project.getFilesDir
import com.rykova_e.kts_project.utils.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

internal const val DATA_STORE_FILE_NAME = "settings.preferences_pb"

fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { "${getFilesDir()}/$DATA_STORE_FILE_NAME".toPath() }
    )
}

class DataStoreSettingsStorage(
    private val dataStore: DataStore<Preferences>
) : SettingsStorage {

    private companion object {
        val FIRST_OPEN = booleanPreferencesKey("first_open")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override fun observeFirstOpen(): Flow<Boolean> =
        dataStore.data.map { prefs -> prefs[FIRST_OPEN] ?: true }

    override suspend fun setFirstOpen(): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs -> prefs[FIRST_OPEN] = false }
    }

    override fun observeAccessToken(): Flow<String> =
        dataStore.data.map { prefs -> prefs[ACCESS_TOKEN].orEmpty() }

    override suspend fun saveAccessToken(value: String): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs -> prefs[ACCESS_TOKEN] = value }
    }

    override fun observeRefreshToken(): Flow<String> =
        dataStore.data.map { prefs -> prefs[REFRESH_TOKEN].orEmpty() }

    override suspend fun saveRefreshToken(value: String): Result<Unit> = suspendRunCatching {
        dataStore.edit { prefs -> prefs[REFRESH_TOKEN] = value }
    }
}