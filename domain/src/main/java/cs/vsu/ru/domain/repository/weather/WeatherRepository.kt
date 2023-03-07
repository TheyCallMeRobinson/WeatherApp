package cs.vsu.ru.domain.repository.weather

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather

interface WeatherRepository {

    fun getWeather(location: Location): Weather
}