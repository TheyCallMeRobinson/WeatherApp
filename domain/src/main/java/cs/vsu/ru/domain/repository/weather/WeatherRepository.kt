package cs.vsu.ru.domain.repository.weather

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather

interface WeatherRepository {

    suspend fun getWeatherIconFromDatabase(iconName: String): ByteArray?
    suspend fun getWeatherIconFromPicasso(iconName: String): ByteArray
    suspend fun getWeather(location: Location): Weather
    suspend fun getHourlyWeather(location: Location): Weather
    fun saveWeatherIconToDatabase(iconName: String, icon: ByteArray)
}