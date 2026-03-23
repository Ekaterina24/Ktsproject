package com.rykova_e.kts_project

import androidx.room.RoomDatabase
import com.rykova_e.kts_project.data.source.local.db.AppDatabase
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.flow.StateFlow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getFilesDir(): String
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

expect class NetworkMonitor {
    constructor()
    val isConnected: StateFlow<Boolean>
}

expect fun provideEngine(): HttpClientEngine
