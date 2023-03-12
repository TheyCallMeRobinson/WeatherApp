package cs.vsu.ru.model.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_icons")
class WeatherIconEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "icon")
    val icon: ByteArray
)