package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class HourlyWeatherResponse(

    @SerializedName(value = "dt")
    private val time: Int,

    @SerializedName(value = "temp")
    private val temperature: Float,

    private val humidity: Float,

    private val weather: WeatherDetailsElement

)