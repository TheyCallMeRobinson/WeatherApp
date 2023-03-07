package cs.vsu.ru.domain.model.weather

data class DailyWeather(

    val date: Int,
    val humidity: Float,
    val temperature: Temperature,
    val weather: DetailsWeather
) {

    data class Temperature(

        val minTemperature: Float,
        val maxTemperature: Float
    )
}
