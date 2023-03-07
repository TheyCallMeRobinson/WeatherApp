package cs.vsu.ru.application

import android.app.Application
import android.arch.persistence.room.Room
import cs.vsu.ru.database.AppDatabase

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
