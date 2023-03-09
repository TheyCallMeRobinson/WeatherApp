package cs.vsu.ru.application.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.domain.model.weather.HourlyWeather
import java.util.Date
import android.text.format.DateFormat
import cs.vsu.ru.application.model.HumidityModel
import cs.vsu.ru.application.model.TemperatureModel

class HourlyListAdapter(
    private val hourlyList: List<HourlyWeather>,
    private val timezoneOffset: Int
) : RecyclerView.Adapter<HourlyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hourly_temperature, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listElement = hourlyList[position]

        val dateFormat = DateFormat.format("HH:mm", Date((listElement.time + timezoneOffset) * 1000L))
        holder.hour.text = dateFormat.toString()
        holder.temperature.text = TemperatureModel(listElement.temperature).toString()
        holder.humidity.text = HumidityModel(listElement.humidity).toString()
    }

    // api provides 48 hours of forecast, but the app needs only first 24
    override fun getItemCount(): Int = hourlyList.size / 2

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hour: TextView
        val temperature: TextView
        val humidity: TextView

        init {
            hour = view.findViewById(R.id.item_hour)
            temperature = view.findViewById(R.id.item_temperature)
            humidity = view.findViewById(R.id.item_humidity)
        }
    }
}
