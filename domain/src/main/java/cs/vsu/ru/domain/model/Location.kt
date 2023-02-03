package cs.vsu.ru.domain.model

private const val DEFAULT_LOCATION = "Moscow"

data class Location(private val locationName: String = DEFAULT_LOCATION) {}