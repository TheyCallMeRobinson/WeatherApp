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
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.motion.SavedLocationsTransitionListener
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.domain.model.location.Location

class SavedLocationsListAdapter(
    private var savedLocations: List<Location>,
    private val setCurrentLocationListener: (Location) -> Unit,
    private val drawerViewModel: DrawerViewModel,
) : RecyclerView.Adapter<SavedLocationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_saved_location, parent, false
        )

        return ViewHolder(view) {
            setCurrentLocationListener(savedLocations[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.locationName.text = savedLocations[position].name
        holder.countryName.text = savedLocations[position].country
        holder.setFavoriteListener = { index ->
            (holder.view as MotionLayout).setTransitionListener(
                SavedLocationsTransitionListener {
                    drawerViewModel.setFavoriteLocation(savedLocations[index])
                }
            )
        }
        holder.removeListener = { index ->
            (holder.view as MotionLayout).setTransitionListener(
                SavedLocationsTransitionListener {
                    drawerViewModel.removeSavedLocation(savedLocations[index])
                }
            )
        }
    }

    override fun getItemCount(): Int = savedLocations.size

    @SuppressLint("ClickableViewAccessibility")
    class ViewHolder(
        val view: View,
        val onViewClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        lateinit var setFavoriteListener: (Int) -> Unit
        lateinit var removeListener: (Int) -> Unit

        val locationName: TextView = view.findViewById(R.id.item_location_name)
        val countryName: TextView = view.findViewById(R.id.item_country_name)
        val setToFavorite: ImageView = view.findViewById(R.id.item_add_to_favorite)
        val removeFromList: ImageView = view.findViewById(R.id.item_remove_from_list)

        init {
            setToFavorite.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    setFavoriteListener(adapterPosition)
                }
                false
            }
            removeFromList.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    removeListener(adapterPosition)
                }
                false
            }

            view.setOnClickListener {
                onViewClickListener(adapterPosition)
            }
            view.isClickable = true
        }
    }
}