package cs.vsu.ru.application.ui.main


import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModel
import cs.vsu.ru.application.R
import cs.vsu.ru.application.WeatherApplication
import java.util.*

class MainViewModel : ViewModel() {

    private lateinit var locationName: String
    private lateinit var today: String
    private var temperatureNow: Int = 0
    private var temperatureHighest: Int = 0
    private var temperatureLowest: Int = 0
    private var temperatureFeelsLike: Int = 0
    private var background: Drawable? = null
    private var backgroundTint: Drawable? = null


    private fun getBackground() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        background = if (currentHour in 8..18) {
            AppCompatResources.getDrawable(WeatherApplication.applicationContext, R.drawable.background_daytime)
        } else {
            AppCompatResources.getDrawable(WeatherApplication.applicationContext, R.drawable.background_nighttime)
        }
    }

    private fun getBackgroundTint() {

    }
}