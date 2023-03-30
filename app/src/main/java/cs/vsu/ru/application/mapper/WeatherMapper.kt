package cs.vsu.ru.application.mapper

import android.graphics.Bitmap
import android.text.format.DateFormat
import cs.vsu.ru.application.model.*
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import java.util.*
import kotlin.math.ceil

class WeatherMapper {

    fun fromEntity(
        entity: Weather,
        location: Location,
        currentWeatherIcon: Bitmap,
        hourlyWeatherIcons: List<Bitmap>,
        dailyWeatherIcons: List<Bitmap>,
        apiCallTime: Long
    ): WeatherUIModel {
        val offsetFromUtc = TimeZone.getDefault().getOffset(Date().time) / 1000
        val dataResponseTimeOffset = entity.timezoneOffsetSeconds - offsetFromUtc

        val hourlyWeather = entity.hourlyWeather
        val currentWeather = entity.currentWeather!!
        val dailyWeather = entity.dailyWeather!!

        val hourlyWeatherList = mutableListOf<HourlyWeather>()
        for (i in hourlyWeatherIcons.indices) {
            hourlyWeatherList.add(
                HourlyWeather(
                    time = DateFormat.format("HH:mm", Date((dataResponseTimeOffset + hourlyWeather[i].time) * 1000L))
                        .toString(),
                    icon = hourlyWeatherIcons[i],
                    temperature = ceil(hourlyWeather[i].temperature).toInt(),
                    humidity = HumidityModel(hourlyWeather[i].humidity).toString()
                )
            )
        }

        val dailyWeatherList = mutableListOf<DailyWeather>()
        for (i in dailyWeatherIcons.indices) {
            dailyWeatherList.add(
                DailyWeather(
                    dayOfWeek = if (i == 0) "Сегодня" else
                        DateFormat.format("EEEE", Date((dataResponseTimeOffset + dailyWeather[i].date) * 1000L))
                            .toString(),
                    humidity = HumidityModel(dailyWeather[i].humidity).toString(),
                    icon = dailyWeatherIcons[i],
                    dayTemperature = TemperatureModel(dailyWeather[i].temperature.maxTemperature).toString(),
                    nightTemperature = TemperatureModel(dailyWeather[i].temperature.minTemperature).toString()
                )
            )
        }

        return WeatherUIModel(
            currentWeather = CurrentWeather(
                currentTemperature = TemperatureModel(currentWeather.temperature).toString(),
                icon = currentWeatherIcon,
                dayNightTemperature =
                    "${TemperatureModel(dailyWeather[0].temperature.minTemperature)} / " +
                    "${TemperatureModel(dailyWeather[0].temperature.maxTemperature)}",
                location = location.name,
                feelsLikeTemperature = "Ощущается как ${TemperatureModel(currentWeather.feelsLike)}",
                sunset = DateFormat.format("HH:mm", Date((dataResponseTimeOffset + currentWeather.sunset) * 1000L))
                    .toString(),
                sunrise = DateFormat.format("HH:mm", Date((dataResponseTimeOffset + currentWeather.sunrise) * 1000L))
                    .toString(),
                uvIndex = ceil(currentWeather.uvIndex).toInt().toString(),
                humidity = HumidityModel(currentWeather.humidity).toString(),
                windSpeed = "${ceil(currentWeather.windSpeed).toInt()} км/ч",
                localDateTime = DateFormat.format("EE, HH:mm", Date((dataResponseTimeOffset + currentWeather.currentTimeSeconds) * 1000L))
                    .toString()
            ),
            hourlyWeather = hourlyWeatherList,
            dailyWeather = dailyWeatherList,
            apiCallTime = "${DateFormat.format("dd.MM, HH:mm", Date(apiCallTime))}",
        )
    }
}