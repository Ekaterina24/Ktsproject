package com.rykova_e.kts_project.domain.use_case

import com.rykova_e.kts_project.domain.repository.CourseRepository

class SingUpOnCourseUseCase(
    private val repository: CourseRepository
) {

    suspend fun execute(courseId: Long) {
        repository.singUpOnCourse(courseId)
    }
}