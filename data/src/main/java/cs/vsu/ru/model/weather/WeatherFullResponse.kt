package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherFullResponse (

    @SerializedName(value = "timezone_offset")
    val timezoneOffset: Int,
    val current: CurrentWeatherResponse,
    val daily: List<DailyWeatherResponse>,
    val hourly: List<HourlyWeatherResponse>,
)
