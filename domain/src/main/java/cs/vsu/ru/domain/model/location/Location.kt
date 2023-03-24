package cs.vsu.ru.domain.model.location

private const val DEFAULT_LOCATION_NAME = "Воронеж"
private const val DEFAULT_ADMIN_AREA_NAME = "Воронежская область"
private const val DEFAULT_LOCATION_COUNTRY = "Россия"
private const val DEFAULT_LOCATION_LATITUDE = 51.672
private const val DEFAULT_LOCATION_LONGITUDE = 39.1843

data class Location(

    val name: String = DEFAULT_LOCATION_NAME,
    val country: String? = DEFAULT_LOCATION_COUNTRY,
    val adminArea: String? = DEFAULT_ADMIN_AREA_NAME,
    val latitude: Double? = DEFAULT_LOCATION_LATITUDE,
    val longitude: Double? = DEFAULT_LOCATION_LONGITUDE
)
