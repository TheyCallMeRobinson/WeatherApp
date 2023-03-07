package cs.vsu.ru.repository

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository
import cs.vsu.ru.environment.WeatherServiceProvider

class WeatherRepositoryImpl(private val serviceProvider: WeatherServiceProvider) : WeatherRepository {

    override suspend fun getWeather(location: Location): Weather {
        val service = serviceProvider.weatherService
        TODO("Make coroutine call")
        //return Weather()
    }
}