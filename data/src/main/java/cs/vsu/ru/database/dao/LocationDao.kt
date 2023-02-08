package cs.vsu.ru.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import cs.vsu.ru.model.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    fun getAll(): List<LocationEntity>?

    @Query("SELECT SINGLE(*) FROM locations where name = :name")
    fun getByName(name: String): LocationEntity?

    @Query("DELETE FROM locations WHERE name = :name")
    fun deleteByName(name: String)

    @Insert
    fun insert(location: LocationEntity)
}