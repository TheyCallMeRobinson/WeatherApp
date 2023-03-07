package cs.vsu.ru.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "country")
    val country: String? = null,

    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,

    @ColumnInfo(name = "longitude")
    val longitude: Double? = null

)