package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.application.R
import cs.vsu.ru.domain.usecase.location.GetCurrentLocationUseCase
import cs.vsu.ru.domain.usecase.weather.GetWeatherDataUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers
import java.util.*

class MainViewModel(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private lateinit var locationName: String
    private lateinit var today: String
    private var temperatureNow: Int = 0
    private var temperatureHighest: Int = 0
    private var temperatureLowest: Int = 0
    private var temperatureFeelsLike: Int = 0

    public val backgroundResource = MutableLiveData<Int>().apply {
        value = getBackground()
    }

    private fun getBackground(): Int? {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val background = if (currentHour in 8..18) {
//            AppCompatResources.getDrawable(WeatherApplication.applicationContext, R.drawable.background_daytime)
            R.drawable.background_daytime
        } else {
//            AppCompatResources.getDrawable(WeatherApplication.applicationContext, R.drawable.background_nighttime)
            R.drawable.background_nighttime
        }
        return background
    }

    fun getWeatherData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val location = getCurrentLocationUseCase.execute()
            emit(Resource.success(data = getWeatherDataUseCase.execute(location)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
