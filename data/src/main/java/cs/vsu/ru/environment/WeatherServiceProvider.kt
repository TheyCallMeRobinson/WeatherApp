package cs.vsu.ru.environment

import cs.vsu.ru.service.WeatherService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val openWeatherApiVersion = "3.0"

object WeatherServiceProvider {

    // This is an open source api key, it is safe to leave it here exposed like this
    const val openWeatherApiKey = "f914d926992e450dee0e46cd2a46caaf"
    private const val baseUrl = "https://api.openweathermap.org/data/$openWeatherApiVersion/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}
