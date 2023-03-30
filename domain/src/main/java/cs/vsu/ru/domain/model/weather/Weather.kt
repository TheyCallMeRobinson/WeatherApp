package cs.vsu.ru.domain.model.weather

data class Weather(

    val timezoneOffsetSeconds: Int,
    val currentWeather: CurrentWeather?,
    val dailyWeather: List<DailyWeather>?,
    val hourlyWeather: List<HourlyWeather>,
)
