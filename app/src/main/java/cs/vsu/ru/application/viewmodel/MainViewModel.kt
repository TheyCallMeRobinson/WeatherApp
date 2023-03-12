package cs.vsu.ru.application.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.application.R
import cs.vsu.ru.application.mapper.WeatherMapper
import cs.vsu.ru.domain.usecase.location.GetCurrentLocationUseCase
import cs.vsu.ru.domain.usecase.weather.GetWeatherDataUseCase
import cs.vsu.ru.domain.usecase.weather.GetWeatherIconUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers
import java.util.*

class MainViewModel(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getWeatherIconUseCase: GetWeatherIconUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val weatherMapper: WeatherMapper,
) : ViewModel() {

    val backgroundResource = MutableLiveData<Int>().apply {
        value = getBackground()
    }

    private fun getBackground(): Int {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val background = if (currentHour in 8..18) {
            R.drawable.background_daytime
        } else {
            R.drawable.background_nighttime
        }
        return background
    }

    fun getWeatherData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val location = getCurrentLocationUseCase.execute()

            val weatherData = getWeatherDataUseCase.execute(location)

            val currentWeatherIcon =
                getWeatherIcon(weatherData.currentWeather.detailsWeather.icon)
            val hourlyWeatherIcons =
                getWeatherIconList(weatherData.hourlyWeather.map { it.weather.icon })
            val dailyWeatherIcons =
                getWeatherIconList(weatherData.dailyWeather.map { it.weather.icon })

            val weatherDataToDisplay = weatherMapper.fromEntity(
                weatherData,
                location,
                byteArrayToBitmap(currentWeatherIcon),
                byteArrayListToBitmap(hourlyWeatherIcons),
                byteArrayListToBitmap(dailyWeatherIcons),
            )

            emit(Resource.success(data = weatherDataToDisplay))
        } catch (exception: Exception) {

            Log.e("Error", exception.stackTraceToString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private suspend fun getWeatherIcon(iconName: String) =
        getWeatherIconUseCase.execute(iconName)

    private suspend fun getWeatherIconList(iconNames: List<String>): List<ByteArray> {
        val iconList = mutableListOf<ByteArray>()
        iconNames.forEach {
            iconList.add(getWeatherIcon(it))
        }
        return iconList
    }

    private fun byteArrayListToBitmap(byteArrayList: List<ByteArray>): List<Bitmap> =
        byteArrayList.map {
            byteArrayToBitmap(it)
        }

    private fun byteArrayToBitmap(byteArray: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
