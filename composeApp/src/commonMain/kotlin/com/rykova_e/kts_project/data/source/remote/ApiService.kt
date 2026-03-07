package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.source.remote.model.CourseWrapper
import com.rykova_e.kts_project.data.source.remote.model.SearchWrapper
import com.rykova_e.kts_project.data.source.remote.model.WrapperCourses
import com.rykova_e.kts_project.data.source.remote.model.WrapperUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val httpClient: HttpClient) {

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
}