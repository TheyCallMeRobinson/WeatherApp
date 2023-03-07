package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class DailyWeatherResponse(

    @SerializedName(value = "dt")
    val date: Int,

    val humidity: Float,

    @SerializedName(value = "temp")
    val temperature: Temperature,

    val weather: WeatherDetailsElement

) {

    data class Temperature(

        @SerializedName(value = "min")
        val minTemperature: Float,

        @SerializedName(value = "max")
        val maxTemperature: Float
    )
}
