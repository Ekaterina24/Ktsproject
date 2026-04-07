package com.rykova_e.kts_project

import androidx.room.Room
import androidx.room.RoomDatabase
import com.rykova_e.kts_project.data.source.local.db.AppDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual fun getFilesDir(): String {
    return NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory, NSUserDomainMask, true
    ).first() as String
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = NSHomeDirectory() + "/Documents/app-database"
    return Room.databaseBuilder<AppDatabase>(name = dbFile)
}