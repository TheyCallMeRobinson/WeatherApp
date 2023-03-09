package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class HourlyWeatherResponse(

    @SerializedName(value = "dt")
    val time: Int,

    @SerializedName(value = "temp")
    val temperature: Float,

    val humidity: Float,

    val weather: List<WeatherDetailsElement>
)
