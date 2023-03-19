package cs.vsu.ru.application.view.add_location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cs.vsu.ru.application.databinding.FragmentAddNewLocationBinding
import cs.vsu.ru.application.view.adapter.NewLocationsListAdapter
import cs.vsu.ru.application.viewmodel.AddNewLocationViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewLocationFragment : Fragment() {

    private lateinit var binding: FragmentAddNewLocationBinding
    private val viewModel by viewModel<AddNewLocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)

        binding.fragmentFindLocationsBtn.setOnClickListener {
            getLocationsByName(binding.fragmentAddNewLocationEt.text.toString())
        }

        return binding.root
    }

    private fun getLocationsByName(locationName: String) {
        viewModel.getLocationsByName(locationName).observe(viewLifecycleOwner) {
            it?.let {
                when(it.status) {
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        setLocations(it.data)
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setLocations(locations: List<Location>?) {
        val newLocationsListAdapter = NewLocationsListAdapter(locations) {
            viewModel.saveLocation(it)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        val newLocationsList = binding.fragmentNewLocationsList
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        newLocationsList.layoutManager = linearLayoutManager
        newLocationsList.adapter = newLocationsListAdapter
        newLocationsList.setHasFixedSize(true)
    }

}
