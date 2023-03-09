package cs.vsu.ru.domain.model.weather

data class Weather(

    val currentWeather: CurrentWeather,
    val dailyWeather: List<DailyWeather>,
    val hourlyWeather: List<HourlyWeather>,
    val alerts: List<AlertWeather>?
)
