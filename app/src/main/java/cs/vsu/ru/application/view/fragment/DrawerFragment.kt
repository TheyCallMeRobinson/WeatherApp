package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentDrawerBinding
import cs.vsu.ru.application.motion.SavedLocationsTransitionListener
import cs.vsu.ru.application.view.adapter.SavedLocationsListAdapter
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawerFragment : Fragment() {

    private val savedLocationsTransitionListener = SavedLocationsTransitionListener()
    private lateinit var binding: FragmentDrawerBinding
    private val viewModel by viewModel<DrawerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerBinding.inflate(inflater)

        binding.drawerAddNewLocationBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_drawerFragment_to_addNewLocation)
        )

        binding.drawerAboutBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_drawerFragment_to_aboutFragment)
        )

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.getFavoriteLocation().observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        setFavoriteLocation(it.data!!)
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.getSavedLocations().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setSavedLocationsList(it.data!!)
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setFavoriteLocation(favoriteLocation: Location) {
        binding.drawerFavoritePlace.itemLocationName.text = favoriteLocation.name
        binding.drawerFavoritePlace.itemCountryName.text = favoriteLocation.country
    }

    private fun setSavedLocationsList(savedLocationsList: List<Location>) {

        val savedLocationsListAdapter = SavedLocationsListAdapter(
            savedLocationsList.toMutableList(), {
                viewModel.setFavoriteLocation(it)
                Toast.makeText(context, "Location set as favorite", Toast.LENGTH_LONG).show()
            }, {
//                viewModel.removeSavedLocation(it)
                Toast.makeText(context, "Location removed from list", Toast.LENGTH_LONG).show()
            }, {
                it.setTransitionListener(savedLocationsTransitionListener)
            }
        )
        val linearLayoutManager = LinearLayoutManager(context)
        val savedLocations = binding.drawerFavoriteLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        savedLocations.layoutManager = linearLayoutManager
        savedLocations.adapter = savedLocationsListAdapter
        savedLocations.setHasFixedSize(true)
    }
}
