package cs.vsu.ru.service

import cs.vsu.ru.model.weather.WeatherFullResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET
    fun getForecast(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query(value = "exclude", encoded = true) exclude: String?,
        @Query("appid") key: String?,
        @Query("units") units: String?,
        @Query("lang") language: String?
    ): Call<WeatherFullResponse?>?
}
