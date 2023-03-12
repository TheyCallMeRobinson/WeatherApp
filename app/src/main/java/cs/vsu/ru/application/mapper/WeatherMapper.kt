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
    ): WeatherDataModel {

        val hourlyWeatherList = mutableListOf<HourlyWeather>()
        for (i in hourlyWeatherIcons.indices) {
            hourlyWeatherList.add(
                HourlyWeather(
                    time = DateFormat.format("HH:mm", Date((entity.hourlyWeather[i].time) * 1000L))
                        .toString(),
                    icon = hourlyWeatherIcons[i],
                    temperature = TemperatureModel(entity.hourlyWeather[i].temperature).toString(),
                    humidity = HumidityModel(entity.hourlyWeather[i].humidity).toString()
                )
            )
        }

        val dailyWeatherList = mutableListOf<DailyWeather>()
        for (i in dailyWeatherIcons.indices) {
            dailyWeatherList.add(
                DailyWeather(
                    dayOfWeek = if (i == 0) "Сегодня" else
                        DateFormat.format("EEEE", Date(entity.dailyWeather[i].date * 1000L))
                            .toString(),
                    humidity = HumidityModel(entity.dailyWeather[i].humidity).toString(),
                    icon = dailyWeatherIcons[i],
                    dayTemperature = TemperatureModel(entity.dailyWeather[i].temperature.maxTemperature).toString(),
                    nightTemperature = TemperatureModel(entity.dailyWeather[i].temperature.minTemperature).toString()
                )
            )
        }

        return WeatherDataModel(
            currentWeather = CurrentWeather(
                currentTemperature = TemperatureModel(entity.currentWeather.temperature).toString(),
                icon = currentWeatherIcon,
                dayNightTemperature =
                    "${TemperatureModel(entity.dailyWeather[0].temperature.minTemperature)} / " +
                    "${TemperatureModel(entity.dailyWeather[0].temperature.maxTemperature)}",
                location = location.name,
                feelsLikeTemperature = "Ощущается как ${TemperatureModel(entity.currentWeather.feelsLike)}",
                sunset = DateFormat.format("HH:mm", Date(entity.currentWeather.sunset * 1000L))
                    .toString(),
                sunrise = DateFormat.format("HH:mm", Date(entity.currentWeather.sunrise * 1000L))
                    .toString(),
                uvIndex = ceil(entity.currentWeather.uvIndex).toInt().toString(),
                humidity = HumidityModel(entity.currentWeather.humidity).toString(),
                windSpeed = "${ceil(entity.currentWeather.windSpeed).toInt()} км/ч"
            ),
            hourlyWeather = hourlyWeatherList,
            dailyWeather = dailyWeatherList,
            apiCallTime = "${DateFormat.format("dd.MM, HH:mm", Date(apiCallTime))} Обновл.",
        )
    }
}