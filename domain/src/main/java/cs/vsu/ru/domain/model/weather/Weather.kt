package cs.vsu.ru.domain.model.weather

data class Weather(

    private val currentWeather: CurrentWeather,
    private val dailyWeather: List<DailyWeather>,
    private val hourlyWeather: List<HourlyWeather>,
    private val alerts: List<AlertWeather>
)
