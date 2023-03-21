package cs.vsu.ru.application.view.adapter

import android.annotation.SuppressLint
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
import cs.vsu.ru.domain.model.location.Location

class SavedLocationsListAdapter(
    private val savedLocations: MutableList<Location>,
    private val setToFavoriteElementListener: (Location) -> Unit,
    private val removeElementListener: (Location) -> Unit,
    private val motionListener: (MotionLayout) -> Unit
) : RecyclerView.Adapter<SavedLocationsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_saved_location, parent, false
        )

        return ViewHolder(view, {
            setToFavoriteElementListener(savedLocations[it])
        }, {
            removeElementListener(savedLocations[it])
        }, {
            motionListener(it as MotionLayout)
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.locationName.text = savedLocations[position].name
        holder.countryName.text = savedLocations[position].country
    }

    override fun getItemCount(): Int = savedLocations.size

    @SuppressLint("ClickableViewAccessibility")
    class ViewHolder(
        view: View,
        setFavoriteListener: (Int) -> Unit,
        removeListener: (Int) -> Unit,
        motionListener: (View) -> Unit
    ) : RecyclerView.ViewHolder(view) {

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
                if (event.action == MotionEvent.ACTION_UP) {
                    setFavoriteListener(adapterPosition)
                    motionListener(view)
                }
                false
            }

            removeFromList.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    removeListener(adapterPosition)
                    motionListener(view)
                }
                false
            }
        }
    }
}