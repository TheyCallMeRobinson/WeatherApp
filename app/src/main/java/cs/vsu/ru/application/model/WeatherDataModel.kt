package cs.vsu.ru.application.model

import android.graphics.Bitmap

data class WeatherDataModel(
    val currentWeather: CurrentWeather,
    val hourlyWeather: List<HourlyWeather>,
    val dailyWeather: List<DailyWeather>,
    val apiCallTime: String
)

data class CurrentWeather(
    val location: String,
    val icon: Bitmap,
    val currentTemperature: String,
    val dayNightTemperature: String,
    val feelsLikeTemperature: String,
    val sunset: String,
    val sunrise: String,
    val uvIndex: String,
    val humidity: String,
    val windSpeed: String,
    val localDateTime: String
)

data class HourlyWeather(
    val time: String,
    val icon: Bitmap,
    val temperature: String,
    val humidity: String,
)

data class DailyWeather(
    val dayOfWeek: String,
    val humidity: String,
    val icon: Bitmap,
    val dayTemperature: String,
    val nightTemperature: String,
)