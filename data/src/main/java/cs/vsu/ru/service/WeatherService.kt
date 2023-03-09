package cs.vsu.ru.service

import cs.vsu.ru.environment.WeatherServiceProvider
import cs.vsu.ru.model.weather.WeatherFullResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(".")
    suspend fun getForecast(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query(value = "exclude", encoded = true) exclude: String? = "current,minutely,alerts",
        @Query("appid") key: String = WeatherServiceProvider.openWeatherApiKey,
        @Query("units") units: String? = "metric",
        @Query("lang") language: String? = "ru"
    ): WeatherFullResponse
}
