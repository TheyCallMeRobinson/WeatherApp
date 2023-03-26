package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_left)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_right)

        binding.fragmentBackBtn.setOnClickListener {
            navigateBack()
        }

        binding.fragmentClearInputBtn.setOnClickListener {
            clearInputField()
        }

        binding.fragmentAddNewLocationInputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                getLocationsByName(binding.fragmentAddNewLocationInputEt.text.toString())
            }
        })

        binding.fragmentAddNewLocationInputEt.requestFocus()

        return binding.root
    }

    private fun getLocationsByName(locationName: String) {
        viewModel.getLocationsByName(locationName).observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        binding.fragmentAddNewLocationInputHolder.isErrorEnabled = false
                    }
                    Status.SUCCESS -> {
                        setLocations(it.data)
                    }
                    Status.ERROR -> {
                        binding.fragmentAddNewLocationInputHolder.isErrorEnabled = true
                        binding.fragmentAddNewLocationInputHolder.error = it.message
                    }
                }
            }
        }
    }

    private fun setLocations(locations: List<Location>?) {
        binding.fragmentListIsEmptyTv.visibility = if (locations?.isEmpty() == true) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

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

    private fun clearInputField() {
        binding.fragmentAddNewLocationInputEt.text?.clear()
        binding.fragmentAddNewLocationInputHolder.isErrorEnabled = false
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}
