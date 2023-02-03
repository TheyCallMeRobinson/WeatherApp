package cs.vsu.ru.application.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cs.vsu.ru.application.R
import java.util.*

class MainViewModel : ViewModel() {

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
}