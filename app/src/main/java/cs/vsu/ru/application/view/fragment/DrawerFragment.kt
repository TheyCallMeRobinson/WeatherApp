package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentDrawerBinding
import cs.vsu.ru.application.view.adapter.SavedLocationsListAdapter
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawerFragment : Fragment() {

    private lateinit var binding: FragmentDrawerBinding
    private val drawerViewModel by viewModel<DrawerViewModel>()
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerBinding.inflate(inflater)

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
                    Status.ERROR -> {}
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
                    Status.ERROR -> {}
                }
            }
        }
    }

    private fun setFavoriteLocation(favoriteLocation: Location) {
        binding.drawerFavoritePlace.itemLocationName.text = favoriteLocation.name
        binding.drawerFavoritePlace.itemCountryName.text = favoriteLocation.country
    }

    private fun setSavedLocationsList(savedLocationsList: List<Location>) {
        if (savedLocationsList.isEmpty()) {
            binding.drawerNothingToShowTv.visibility = View.VISIBLE
            return
        }

        binding.drawerNothingToShowTv.visibility = View.INVISIBLE

        val savedLocationsListAdapter = SavedLocationsListAdapter(
            savedLocationsList,
            mainViewModel,
            drawerViewModel
        )
        val linearLayoutManager = LinearLayoutManager(context)
        val savedLocations = binding.drawerFavoriteLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        savedLocations.layoutManager = linearLayoutManager
        savedLocations.adapter = savedLocationsListAdapter
        savedLocations.setHasFixedSize(true)
    }
}
