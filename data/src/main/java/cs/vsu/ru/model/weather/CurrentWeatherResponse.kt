package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(

    @SerializedName(value = "temp")
    val temperature: Float,

    @SerializedName(value = "feels_like")
    val feelsLike: Float,

    val sunrise: Int,
    val sunset: Int,

    @SerializedName(value = "uvi")
    val uvIndex: Float,

    val humidity: Float,

    @SerializedName(value = "wind_speed")
    val windSpeed: Float,

    val weather: List<WeatherDetailsElement>
)