package cs.vsu.ru.domain.model.weather

data class CurrentWeather(

    val currentTimeSeconds: Long,
    val temperature: Float,
    val feelsLike: Float,
    val sunrise: Int,
    val sunset: Int,
    val uvIndex: Float,
    val humidity: Float,
    val windSpeed: Float,
    val detailsWeather: DetailsWeather
)