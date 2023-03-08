package cs.vsu.ru.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.model.location.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
