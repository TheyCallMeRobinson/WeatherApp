package cs.vsu.ru.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.model.location.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
