package cs.vsu.ru.environment

import com.google.gson.GsonBuilder
import cs.vsu.ru.service.WeatherDataService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val openWeatherApiVersion = "3.0"

object WeatherServiceProvider {

    // This is an open source api key, it is safe to leave it here exposed like this
    const val openWeatherApiKey = "f914d926992e450dee0e46cd2a46caaf"
    private const val weatherDataBaseUrl = "https://api.openweathermap.org/data/$openWeatherApiVersion/"
    const val weatherIconBaseUrl = "https://openweathermap.org/img/wn/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient.Builder().build())

    val weatherDataService: WeatherDataService = retrofit
        .baseUrl(weatherDataBaseUrl)
        .build()
        .create(WeatherDataService::class.java)
}
