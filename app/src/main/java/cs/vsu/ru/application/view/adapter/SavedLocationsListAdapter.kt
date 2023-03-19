package cs.vsu.ru.application.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.domain.model.location.Location

class SavedLocationsListAdapter(
    private val savedLocations: List<Location>
) : RecyclerView.Adapter<SavedLocationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_saved_location, parent, false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.locationName.text = savedLocations[position].name
        holder.countryName.text = savedLocations[position].country
    }

    override fun getItemCount(): Int = savedLocations.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationName: TextView
        val countryName: TextView

        init {
            locationName = view.findViewById(R.id.item_location_name)
            countryName = view.findViewById(R.id.item_country_name)
        }
    }
}