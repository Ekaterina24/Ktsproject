package com.rykova_e.kts_project.app

import android.app.Application
import com.rykova_e.kts_project.presentation.di.commonDataModule
import com.rykova_e.kts_project.presentation.di.dataModule
import com.rykova_e.kts_project.presentation.di.databaseModule
import com.rykova_e.kts_project.presentation.di.domainModule
import com.rykova_e.kts_project.presentation.di.platformViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@App)
            modules(
                commonDataModule,
                domainModule,
                dataModule(),
                platformViewModelModule,
                databaseModule,
            )
        }
    }
}