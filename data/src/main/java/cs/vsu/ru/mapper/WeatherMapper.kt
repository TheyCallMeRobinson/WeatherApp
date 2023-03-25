package cs.vsu.ru.mapper

import cs.vsu.ru.domain.model.weather.*
import cs.vsu.ru.model.weather.*

class WeatherMapper {

    fun toEntity(dto: WeatherFullResponse): Weather {
        return Weather(
            timezoneOffsetSeconds = dto.timezoneOffsetSeconds,
            currentWeather = CurrentWeather(
                currentTimeSeconds = dto.current.currentTimeSeconds,
                temperature = dto.current.temperature,
                feelsLike = dto.current.feelsLike,
                sunrise = dto.current.sunrise,
                sunset = dto.current.sunset,
                uvIndex = dto.current.uvIndex,
                humidity = dto.current.humidity,
                windSpeed = dto.current.windSpeed,
                detailsWeather = DetailsWeather(
                    id = dto.current.weather[0].id,
                    icon = dto.current.weather[0].icon
                ),
            ),
            dailyWeather = dto.daily.map {
                DailyWeather(
                    date = it.date,
                    humidity = it.humidity,
                    temperature = DailyWeather.Temperature(
                        minTemperature = it.temperature.minTemperature,
                        maxTemperature = it.temperature.maxTemperature
                    ),
                    weather = DetailsWeather(
                        id = it.weather[0].id,
                        icon = it.weather[0].icon
                    )
                )
            },
            hourlyWeather = dto.hourly.subList(0, dto.hourly.size / 2).map {
                HourlyWeather(
                    time = it.time,
                    temperature = it.temperature,
                    humidity = it.humidity,
                    weather = DetailsWeather(
                        id = it.weather[0].id,
                        icon = it.weather[0].icon
                    )
                )
            }
        )
    }
}