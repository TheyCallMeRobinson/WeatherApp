package cs.vsu.ru.domain.model.weather

data class CurrentWeather(

    private val temperature: Float,
    private val feelsLike: Float,
    private val sunrise: Int,
    private val sunset: Int,
    private val uvIndex: Float,
    private val humidity: Float,
    private val windSpeed: Float,
)