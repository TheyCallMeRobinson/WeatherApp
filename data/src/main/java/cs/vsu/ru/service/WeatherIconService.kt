package cs.vsu.ru.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import cs.vsu.ru.environment.WeatherServiceProvider

class WeatherIconService(private val context: Context) {

    suspend fun getWeatherIcon(iconName: String): Bitmap {
        val request = ImageRequest.Builder(context)
            .data("${WeatherServiceProvider.weatherIconBaseUrl}${iconName}@2x.png")
            .allowHardware(false)
            .build()

        val iconDrawable = context.imageLoader.execute(request).drawable
        return (iconDrawable as BitmapDrawable).bitmap
    }
}
