package cs.vsu.ru.application.ui

import android.app.Application
import android.arch.persistence.room.Room
import cs.vsu.ru.database.AppDatabase

class WeatherApplication : Application() {

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }
}
