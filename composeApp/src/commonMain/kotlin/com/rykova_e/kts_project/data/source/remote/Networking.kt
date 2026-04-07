package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object Networking {
    val dataStore = DataStoreSettingsStorage()
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var _token = ""
    init {
        scope.launch {
            dataStore.observeAccessToken()
                .take(1)
                .collect {
                    _token = it
                }
        }
    }

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = "Ktor")
                }
            }
            level = LogLevel.BODY
        }

        defaultRequest {
            url("https://stepik.org/api/")
            header("Authorization", "Bearer $_token")
            contentType(ContentType.Application.Json)
        }
    }
}