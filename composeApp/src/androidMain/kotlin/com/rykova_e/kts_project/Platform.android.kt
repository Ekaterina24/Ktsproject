package com.rykova_e.kts_project

import android.content.Context
import android.os.Build

lateinit var appContext: Context
    private set

fun initContext(context: Context) {
    appContext = context.applicationContext
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getFilesDir(): String = appContext.filesDir.absolutePath