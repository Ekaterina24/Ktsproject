package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.data.source.remote.model.CourseWrapper
import com.rykova_e.kts_project.data.source.remote.model.CurrentUserWrapper
import com.rykova_e.kts_project.data.source.remote.model.ReviewWrapper
import com.rykova_e.kts_project.data.source.remote.model.SearchWrapper
import com.rykova_e.kts_project.data.source.remote.model.WrapperCourses
import com.rykova_e.kts_project.data.source.remote.model.WrapperUser
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
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

class ApiService(private val dataStore: SettingsStorage) {

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

    private val httpClient = HttpClient {
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

    suspend fun getCourses(page: Int = 1): WrapperCourses {
        return httpClient.get("courses") {
            parameter("is_featured", true)
            parameter("page", page)
        }.body()
    }

    suspend fun getUser(id: Long): WrapperUser {
        return httpClient.get("users/$id").body()
    }

    suspend fun searchCourses(query: String, page: Int): SearchWrapper {
        return httpClient.get("search-results") {
            parameter("query", query)
            parameter("page", page)
            parameter("types", "course")
        }.body()
    }

    suspend fun getCourseById(id: Long): CourseWrapper {
        return httpClient.get("courses/$id").body()
    }

    suspend fun getReviewCourseById(id: Long): ReviewWrapper {
        return httpClient.get("course-review-summaries/$id").body()
    }

    suspend fun getUserProfile(): CurrentUserWrapper {
        return httpClient.get("stepics/1").body()
    }
}