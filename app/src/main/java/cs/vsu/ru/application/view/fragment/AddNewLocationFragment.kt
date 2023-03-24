package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentAddNewLocationBinding
import cs.vsu.ru.application.view.adapter.NewLocationsListAdapter
import cs.vsu.ru.application.viewmodel.AddNewLocationViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewLocationFragment : Fragment() {

    private lateinit var binding: FragmentAddNewLocationBinding
    private val viewModel by viewModel<AddNewLocationViewModel>()

    private val wrongInputErrorText = "Это поле не может быть пустым"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)

        binding.fragmentBackBtn.setOnClickListener {
            navigateBack()
        }

        binding.fragmentFindLocationsBtn.setOnClickListener {
            getLocationsByName(binding.fragmentAddNewLocationInputEt.text.toString())
        }

        return binding.root
    }

    private fun getLocationsByName(locationName: String) {
        viewModel.getLocationsByName(locationName).observe(viewLifecycleOwner) {
            it?.let {
                when(it.status) {
                    Status.LOADING -> {
                        binding.fragmentAddNewLocationInputHolder.isErrorEnabled = false
                    }
                    Status.SUCCESS -> {
                        setLocations(it.data)
                    }
                    Status.ERROR -> {
                        binding.fragmentAddNewLocationInputHolder.isErrorEnabled = true
                        binding.fragmentAddNewLocationInputHolder.error = wrongInputErrorText
                    }
                }
            }
        }
    }

    private fun setLocations(locations: List<Location>?) {
        if (locations?.isEmpty() == true) {
            binding.fragmentListIsEmptyTv.visibility = View.VISIBLE
            return
        }

        binding.fragmentListIsEmptyTv.visibility = View.INVISIBLE

        val newLocationsListAdapter = NewLocationsListAdapter(locations) {
            viewModel.saveLocation(it)
            navigateBack()
        }
        val linearLayoutManager = LinearLayoutManager(context)
        val newLocationsList = binding.fragmentNewLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        newLocationsList.layoutManager = linearLayoutManager
        newLocationsList.adapter = newLocationsListAdapter
        newLocationsList.setHasFixedSize(true)
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_addNewLocation_to_drawerFragment)
    }

}
