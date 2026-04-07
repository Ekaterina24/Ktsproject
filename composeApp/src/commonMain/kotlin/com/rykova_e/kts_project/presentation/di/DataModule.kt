package com.rykova_e.kts_project.presentation.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rykova_e.kts_project.data.source.CourseRepositoryCommonImpl
import com.rykova_e.kts_project.data.source.GetCourseRepositoryCommonImpl
import com.rykova_e.kts_project.data.source.local.data_store.DataStoreSettingsStorage
import com.rykova_e.kts_project.data.source.local.data_store.SettingsStorage
import com.rykova_e.kts_project.data.source.local.data_store.createDataStore
import com.rykova_e.kts_project.data.source.local.db.repository.CourseRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.ReviewRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.local.db.repository.UserRepositoryLocalImpl
import com.rykova_e.kts_project.data.source.remote.ApiService
import com.rykova_e.kts_project.data.source.remote.CourseRepositoryImpl
import com.rykova_e.kts_project.data.source.remote.UserRepositoryImpl
import com.rykova_e.kts_project.domain.repository.CourseRepository
import com.rykova_e.kts_project.domain.repository.UserRepository
import com.rykova_e.kts_project.domain.repository.local.CourseRepositoryLocal
import com.rykova_e.kts_project.domain.repository.local.ReviewRepositoryLocal
import com.rykova_e.kts_project.domain.repository.local.UserRepositoryLocal
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun dataModule(): Module

val commonDataModule = module {

    single<DataStore<Preferences>> {
        createDataStore()
    }

    single<SettingsStorage> {
        DataStoreSettingsStorage(get())
    }

    single { ApiService(get()) }

    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<CourseRepository> { CourseRepositoryImpl(get()) }

    factory<CourseRepositoryLocal> { CourseRepositoryLocalImpl(get()) }
    factory<ReviewRepositoryLocal> { ReviewRepositoryLocalImpl(get()) }
    factory<UserRepositoryLocal> { UserRepositoryLocalImpl(get()) }

    single {
        CourseRepositoryCommonImpl(
            courseRepository = get(),
            courseRepositoryLocal = get(),
            userRepository = get(),
            userRepositoryLocal = get(),
            reviewRepositoryLocal = get()
        )
    }

    single {
        GetCourseRepositoryCommonImpl(
            courseRepository = get(),
            courseRepositoryLocal = get(),
            userRepository = get(),
            userRepositoryLocal = get(),
            reviewRepositoryLocal = get()
        )
    }
}
