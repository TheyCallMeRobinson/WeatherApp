package cs.vsu.ru.domain.repository.weather

import cs.vsu.ru.domain.model.weather.Weather

interface WeatherRepository {

    fun getWeather(): Weather
}