package cs.vsu.ru.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.database.dao.WeatherIconDao
import cs.vsu.ru.model.location.LocationEntity
import cs.vsu.ru.model.weather.WeatherIconEntity

@Database(
    entities = [LocationEntity::class, WeatherIconEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao
    abstract fun weatherPictureDao(): WeatherIconDao
}
