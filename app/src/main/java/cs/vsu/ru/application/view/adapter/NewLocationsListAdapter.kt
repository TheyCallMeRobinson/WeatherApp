package cs.vsu.ru.application.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.domain.model.location.Location

class NewLocationsListAdapter(
    private val newLocations: List<Location>?,
    private val clickListener: (Location) -> Unit
) : RecyclerView.Adapter<NewLocationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_new_location, parent, false
            )
        ) { position ->
            newLocations?.let { it[position] }?.let { clickListener(it) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.locationName.text = newLocations?.get(position)?.name
        holder.countryName.text = newLocations?.get(position)?.country
        holder.longitude.text = "%.4f".format(newLocations?.get(position)?.longitude)
        holder.latitude.text = "%.4f".format(newLocations?.get(position)?.latitude)


    }

    override fun getItemCount(): Int = newLocations?.size ?: 0

    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val locationName: TextView
        val countryName: TextView
        val longitude: TextView
        val latitude: TextView

        init {
            locationName = view.findViewById(R.id.item_location_name)
            countryName = view.findViewById(R.id.item_country_name)
            latitude = view.findViewById(R.id.item_latitude_value)
            longitude = view.findViewById(R.id.item_longitude_value)

            view.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }
}