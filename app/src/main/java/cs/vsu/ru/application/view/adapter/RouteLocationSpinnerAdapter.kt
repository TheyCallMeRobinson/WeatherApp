package cs.vsu.ru.application.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.application.R

class RouteLocationSpinnerAdapter(
    private val applicationContext: Context,
    private val locations: List<Location>
) : ArrayAdapter<Location>(applicationContext, 0, locations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_spinner_location, parent, false)

        val locationNameTextView = view.findViewById<TextView>(R.id.item_location_name_spinner)
        val countryNameTextView = view.findViewById<TextView>(R.id.item_country_name_spinner)

        locationNameTextView.text = locations[position].name
        countryNameTextView.text = locations[position].country

        return view
    }
}