package com.rykova_e.kts_project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform