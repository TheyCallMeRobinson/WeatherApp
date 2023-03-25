package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentDrawerBinding
import cs.vsu.ru.application.view.adapter.SavedLocationsListAdapter
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawerFragment : Fragment() {

    private lateinit var binding: FragmentDrawerBinding
    private val drawerViewModel by viewModel<DrawerViewModel>()
    private val mainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerBinding.inflate(inflater)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_left)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_right)

        setUpObservers()
        drawerViewModel.refreshData()

        binding.drawerAddNewLocationBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_drawerFragment_to_addNewLocation)
        )

        binding.drawerAboutBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_drawerFragment_to_aboutFragment)
        )

        return binding.root
    }

    private fun setUpObservers() {
        drawerViewModel.favoriteLocationLiveData.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setFavoriteLocation(resource.data!!)
                    }
                    Status.LOADING -> {}
                    Status.ERROR -> {
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        drawerViewModel.savedLocationsLiveData.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.drawerSavedLocationsProgressBar.visibility = View.INVISIBLE
                        setSavedLocationsList(it.data!!)
                    }
                    Status.LOADING -> {
                        binding.drawerSavedLocationsProgressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setFavoriteLocation(favoriteLocation: Location) {
        binding.drawerFavoritePlace.itemLocationName.text = favoriteLocation.name
        binding.drawerFavoritePlace.itemCountryName.text = favoriteLocation.country
        binding.drawerFavoritePlace.root.setOnClickListener {
            setCurrentLocation(favoriteLocation)
        }
    }

    private fun setSavedLocationsList(savedLocationsList: List<Location>) {
        if (savedLocationsList.isEmpty()) {
            binding.drawerNothingToShowTv.visibility = View.VISIBLE
            return
        }

        binding.drawerNothingToShowTv.visibility = View.INVISIBLE

        val savedLocationsListAdapter = SavedLocationsListAdapter(
            savedLocationsList, { location -> setCurrentLocation(location) }, drawerViewModel
        )
        val linearLayoutManager = LinearLayoutManager(context)
        val savedLocations = binding.drawerFavoriteLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        savedLocations.layoutManager = linearLayoutManager
        savedLocations.adapter = savedLocationsListAdapter
        savedLocations.setHasFixedSize(true)
    }

    private fun setCurrentLocation(location: Location) {
        mainViewModel.setCurrentLocation(location)
        activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.closeDrawer(GravityCompat.START)
    }
}
