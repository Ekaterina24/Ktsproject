package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SingUpOnCourseRequest(
    val enrollment: EnrollmentRemote
)

@Serializable
data class EnrollmentRemote(
    val course: Long
)
