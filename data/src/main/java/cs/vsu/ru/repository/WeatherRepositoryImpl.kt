package cs.vsu.ru.repository

import android.content.Context
import android.graphics.Bitmap
import cs.vsu.ru.database.dao.WeatherIconDao
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository
import cs.vsu.ru.environment.WeatherServiceProvider
import cs.vsu.ru.mapper.WeatherMapper
import cs.vsu.ru.model.weather.WeatherIconEntity
import cs.vsu.ru.service.WeatherIconService
import java.io.ByteArrayOutputStream


class WeatherRepositoryImpl(
    private val retrofitServiceProvider: WeatherServiceProvider,
    private val weatherIconService: WeatherIconService,
    private val weatherIconDao: WeatherIconDao,
    private val mapper: WeatherMapper,
    private val context: Context
) : WeatherRepository {

    override suspend fun getWeather(location: Location): Weather {
        return mapper.toEntity(
            retrofitServiceProvider.weatherDataService.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
                key = retrofitServiceProvider.openWeatherApiKey
            )
        )
    }

    override suspend fun getWeatherIconFromDatabase(iconName: String): ByteArray? {
        return weatherIconDao.getByName(iconName)
    }

    override suspend fun getWeatherIconFromPicasso(iconName: String): ByteArray {
        val bitmap = weatherIconService.getWeatherIcon(iconName)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        return stream.toByteArray()
    }

    override fun saveWeatherIconToDatabase(iconName: String, icon: ByteArray) {
        weatherIconDao.saveIcon(WeatherIconEntity(iconName, icon))
    }
}
