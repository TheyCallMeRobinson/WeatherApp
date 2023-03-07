package cs.vsu.ru.model.weather

import com.google.gson.annotations.SerializedName

data class DailyWeatherResponse(

    @SerializedName(value = "dt")
    private val date: Int,

    private val humidity: Float,

    @SerializedName(value = "temp")
    private val temperature: Temperature,

    private val weather: WeatherDetailsElement

) {

    data class Temperature(

        @SerializedName(value = "min")
        private val minTemperature: Float,

        @SerializedName(value = "max")
        private val maxTemperature: Float

    )

}