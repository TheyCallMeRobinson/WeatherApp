package cs.vsu.ru.application.view.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentDrawerBinding
import cs.vsu.ru.application.view.adapter.HourlyListAdapter
import cs.vsu.ru.application.view.adapter.SavedLocationsListAdapter
import cs.vsu.ru.application.view.add_location.AddNewLocationFragment
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Resource
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawerFragment : Fragment() {
    private lateinit var binding: FragmentDrawerBinding
    private val viewModel by viewModel<DrawerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerBinding.inflate(inflater)

        binding.drawerAddNewLocationBtn.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_drawerFragment_to_addNewLocation)
        )

//        binding.drawerAddNewLocationBtn.setOnClickListener {
//            val navController = childFragmentManager.findFragmentById(R.id.main_fragment_content_container)
//        }
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
        val savedLocationsListAdapter = SavedLocationsListAdapter(savedLocationsList)
        val linearLayoutManager = LinearLayoutManager(context)
        val savedLocationsList = binding.drawerFavoriteLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        savedLocationsList.layoutManager = linearLayoutManager
        savedLocationsList.adapter = savedLocationsListAdapter
        savedLocationsList.setHasFixedSize(true)
    }
}
