package cs.vsu.ru.domain.model.weather

data class DailyWeather(

    private val date: Int,
    private val humidity: Float,
    private val temperature: Temperature,
    private val weather: DetailsWeather
) {

    data class Temperature(

        private val minTemperature: Float,
        private val maxTemperature: Float
    )
}
