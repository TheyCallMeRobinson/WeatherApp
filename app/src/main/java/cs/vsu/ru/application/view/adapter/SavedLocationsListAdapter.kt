package cs.vsu.ru.application.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.motion.SavedLocationsTransitionListener
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.domain.model.location.Location

class SavedLocationsListAdapter(
    private var savedLocations: MutableList<Location>,
    private val drawerViewModel: DrawerViewModel,
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
        holder.setFavoriteListener = { index, _ ->
            val previousFavoriteLocation =
                drawerViewModel.setFavoriteLocation(savedLocations[index])
            (holder.view as MotionLayout).setTransitionListener(
                SavedLocationsTransitionListener {
                    previousFavoriteLocation?.let {
                        savedLocations[index] = it
                        notifyItemChanged(index)
                    }
                }
            )
        }
        holder.removeListener = { index, _ ->
            drawerViewModel.removeSavedLocation(savedLocations[index])
            (holder.view as MotionLayout).setTransitionListener(
                SavedLocationsTransitionListener {
                    savedLocations.removeAt(index)
                    notifyItemRemoved(index)
                }
            )
        }
    }

    override fun getItemCount(): Int = savedLocations.size

    @SuppressLint("ClickableViewAccessibility")
    class ViewHolder(
        val view: View,

        ) : RecyclerView.ViewHolder(view) {

        lateinit var setFavoriteListener: (Int, View) -> Unit
        lateinit var removeListener: (Int, View) -> Unit

        val locationName: TextView
        val countryName: TextView
        val setToFavorite: ImageView
        val removeFromList: ImageView

        init {
            locationName = view.findViewById(R.id.item_location_name)
            countryName = view.findViewById(R.id.item_country_name)
            setToFavorite = view.findViewById(R.id.item_add_to_favorite)
            removeFromList = view.findViewById(R.id.item_remove_from_list)

            setToFavorite.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    setFavoriteListener(adapterPosition, view)
                }
                false
            }

            removeFromList.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    removeListener(adapterPosition, view)
                }
                false
            }
        }
    }
}