package com.rykova_e.kts_project.data.source.remote

import com.rykova_e.kts_project.data.source.remote.model.CourseResponse
import com.rykova_e.kts_project.data.source.remote.model.CoursesResponse
import com.rykova_e.kts_project.data.source.remote.model.SearchResponse
import com.rykova_e.kts_project.data.source.remote.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService(private val httpClient: HttpClient) {

    suspend fun getCourses(page: Int = 1): CoursesResponse {
        return httpClient.get(COURSES) {
            parameter(IS_FEATURED, true)
            parameter(PAGE, page)
        }.body()
    }

    suspend fun getUserById(id: Long): UserResponse {
        return httpClient.get("$USERS/$id").body()
    }

    suspend fun getUsersByIds(ids: List<Long>): UserResponse {
        return httpClient.get(USERS) {
            ids.forEach { id ->
                parameter(IDS, id)
            }
        }.body()
    }

    suspend fun searchCourses(query: String, page: Int): SearchResponse {
        return httpClient.get(SEARCH_RESULTS) {
            parameter(QUERY, query)
            parameter(PAGE, page)
            parameter(TYPES, COURSE)
        }.body()
    }

    suspend fun getCourseById(id: Long): CourseResponse {
        return httpClient.get("$COURSES/$id").body()
    }

    suspend fun getCoursesByIds(ids: List<Long>): CourseResponse {
        return httpClient.get(COURSES) {
            ids.forEach { id ->
                parameter(IDS, id)
            }
        }.body()
    }

    companion object {
        const val COURSES = "courses"
        const val IS_FEATURED = "is_featured"
        const val PAGE = "page"
        const val USERS = "users"
        const val QUERY = "query"
        const val SEARCH_RESULTS = "search-results"
        const val TYPES = "types"
        const val COURSE = "course"
        const val IDS = "ids[]"
    }
}
