package cs.vsu.ru.mapper

import cs.vsu.ru.domain.model.weather.*
import cs.vsu.ru.model.weather.*

class WeatherMapper {

    fun toDto(entity: Weather): WeatherFullResponse {
        return WeatherFullResponse(
            timezoneOffset = entity.timezoneOffset,
            current = CurrentWeatherResponse(
                temperature = entity.currentWeather.temperature,
                feelsLike = entity.currentWeather.feelsLike,
                sunrise = entity.currentWeather.sunrise,
                sunset = entity.currentWeather.sunset,
                uvIndex = entity.currentWeather.uvIndex,
                humidity = entity.currentWeather.humidity,
                windSpeed = entity.currentWeather.windSpeed
            ),
            daily = entity.dailyWeather.map {
                DailyWeatherResponse(
                    date = it.date,
                    humidity = it.humidity,
                    temperature = DailyWeatherResponse.Temperature(
                        minTemperature = it.temperature.minTemperature,
                        maxTemperature = it.temperature.maxTemperature
                    ),
                    weather = listOf(
                        WeatherDetailsElement(
                            id = it.weather.id,
                            icon = it.weather.icon
                        )
                    )
                )
            },
            hourly = entity.hourlyWeather.map {
                HourlyWeatherResponse(
                    time = it.time,
                    temperature = it.temperature,
                    humidity = it.humidity,
                    weather = listOf(
                        WeatherDetailsElement(
                            id = it.weather.id,
                            icon = it.weather.icon
                        )
                    )
                )
            }
        )
    }

    fun toEntity(dto: WeatherFullResponse): Weather {
        return Weather(
            timezoneOffset = dto.timezoneOffset,
            currentWeather = CurrentWeather(
                temperature = dto.current.temperature,
                feelsLike = dto.current.feelsLike,
                sunrise = dto.current.sunrise,
                sunset = dto.current.sunset,
                uvIndex = dto.current.uvIndex,
                humidity = dto.current.humidity,
                windSpeed = dto.current.windSpeed
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