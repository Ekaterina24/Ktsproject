package com.rykova_e.kts_project.presentation.di

import android.content.Context
import androidx.room.Room
import com.rykova_e.kts_project.data.source.local.db.AppDatabase
import org.koin.dsl.module


val databaseModule = module {

    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    single { get<AppDatabase>().courseDao() }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().reviewDao() }
}