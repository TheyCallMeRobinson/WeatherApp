package cs.vsu.ru.domain.model.weather

data class HourlyWeather(

    val time: Int,
    val temperature: Float,
    val humidity: Float,
    val weather: DetailsWeather
)