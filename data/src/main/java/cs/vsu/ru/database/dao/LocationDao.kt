package cs.vsu.ru.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cs.vsu.ru.model.location.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    suspend fun getAll(): List<LocationEntity>?

    @Query("SELECT * FROM locations where name = :name")
    suspend fun getByName(name: String): LocationEntity?

    @Query("DELETE FROM locations WHERE name = :name")
    fun deleteByName(name: String)

    @Insert
    fun insert(location: LocationEntity)
}