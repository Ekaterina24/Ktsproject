package com.rykova_e.kts_project.domain.use_case

import com.rykova_e.kts_project.domain.model.UserCoursesDto
import com.rykova_e.kts_project.domain.repository.UserRepository

class GetUserCoursesUseCase(
    private val repository: UserRepository
) {

    suspend fun execute(): List<UserCoursesDto> {
        return repository.getUserCourses()
    }
}