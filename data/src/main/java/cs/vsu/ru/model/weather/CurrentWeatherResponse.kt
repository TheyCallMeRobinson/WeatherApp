package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    @SerializedName(value = "temp")
    private val temperature: Float,

    @SerializedName(value = "feels_like")
    private val feelsLike: Float,

    private val sunrise: Int,

    private val sunset: Int,

    @SerializedName(value = "uvi")
    private val uvIndex: Float,

    private val humidity: Float,

    @SerializedName(value = "wind_speed")
    private val windSpeed: Float

)