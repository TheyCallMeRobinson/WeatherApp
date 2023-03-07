package cs.vsu.ru.domain.model.weather

data class CurrentWeather(

    val temperature: Float,
    val feelsLike: Float,
    val sunrise: Int,
    val sunset: Int,
    val uvIndex: Float,
    val humidity: Float,
    val windSpeed: Float,
)