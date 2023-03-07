package cs.vsu.ru.domain.model.location

private const val DEFAULT_LOCATION_NAME = "Moscow"

data class Location(

    val name: String = DEFAULT_LOCATION_NAME,

    val country: String? = null,

    val latitude: Double? = null,

    val longitude: Double? = null

)