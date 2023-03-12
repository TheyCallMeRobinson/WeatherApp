package cs.vsu.ru.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cs.vsu.ru.model.weather.WeatherIconEntity

@Dao
interface WeatherIconDao {

    @Query("SELECT * FROM weather_icons where name = :name")
    fun getByName(name: String): ByteArray?

    @Insert
    fun saveIcon(entity: WeatherIconEntity)
}