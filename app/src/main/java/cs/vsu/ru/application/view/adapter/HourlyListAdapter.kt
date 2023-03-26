package cs.vsu.ru.application.view.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import cs.vsu.ru.application.model.HourlyWeather
import cs.vsu.ru.application.model.TemperatureModel

class HourlyListAdapter(
    private val hourlyList: List<HourlyWeather>,
    private val iconList: List<Bitmap>,
) : RecyclerView.Adapter<HourlyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hourly_temperature, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listElement = hourlyList[position]

        holder.hour.text = listElement.time
        holder.icon.setImageBitmap(iconList[position])
        holder.temperature.text = TemperatureModel(listElement.temperature.toFloat()).toString()
        holder.humidity.text = listElement.humidity.toString()
    }

    override fun getItemCount(): Int = hourlyList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hour: TextView
        val icon: ImageView
        val temperature: TextView
        val humidity: TextView

        init {
            hour = view.findViewById(R.id.item_hour)
            icon = view.findViewById(R.id.item_weather_icon)
            temperature = view.findViewById(R.id.item_temperature)
            humidity = view.findViewById(R.id.item_humidity)
        }
    }
}
