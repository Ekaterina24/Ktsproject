package com.rykova_e.kts_project.domain.repository

import com.rykova_e.kts_project.domain.model.UserCoursesDto
import com.rykova_e.kts_project.domain.model.UserDto
import com.rykova_e.kts_project.domain.model.UserProfileDto

interface UserRepository {

    suspend fun getUsersByIds(ids: List<Long>): List<UserDto>
    suspend fun getUser(id: Long): UserDto
    suspend fun getUserProfile(): UserProfileDto
    suspend fun getUserCourses(): List<UserCoursesDto>
}