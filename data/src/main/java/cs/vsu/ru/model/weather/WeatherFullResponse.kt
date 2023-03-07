package cs.vsu.ru.model.weather

data class WeatherFullResponse (

    private val current: CurrentWeatherResponse,
    private val daily: List<DailyWeatherResponse>,
    private val hourly: List<HourlyWeatherResponse>,
    private val alerts: List<AlertsResponse>
)
