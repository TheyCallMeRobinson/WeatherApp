package cs.vsu.ru.domain.model.weather

data class HourlyWeather(

    private val time: Int,
    private val temperature: Float,
    private val humidity: Float,
    private val weather: DetailsWeather
)