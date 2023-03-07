package cs.vsu.ru.model.weather

data class WeatherFullResponse (

    val current: CurrentWeatherResponse,
    val daily: List<DailyWeatherResponse>,
    val hourly: List<HourlyWeatherResponse>,
    val alerts: List<AlertsResponse>
)
