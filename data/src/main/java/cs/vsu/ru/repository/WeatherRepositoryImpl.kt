package cs.vsu.ru.repository

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository
import cs.vsu.ru.environment.WeatherServiceProvider
import cs.vsu.ru.mapper.WeatherMapper

class WeatherRepositoryImpl(
    private val serviceProvider: WeatherServiceProvider,
    private val mapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(location: Location): Weather {
        return mapper.toEntity(
            serviceProvider.weatherService.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
                key = serviceProvider.openWeatherApiKey
            )
        )
    }
}
