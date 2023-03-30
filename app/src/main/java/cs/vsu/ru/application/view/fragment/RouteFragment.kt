package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteBinding
import cs.vsu.ru.application.view.adapter.RouteLocationSpinnerAdapter
import cs.vsu.ru.application.viewmodel.RouteViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteFragment : Fragment() {

    private val viewModel by viewModel<RouteViewModel>()
    private lateinit var binding: FragmentRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_bottom)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_top)

        viewModel.refreshData()
        setupObservers()

        binding.toFragmentMainBtn.setOnClickListener {
            navigateBack()
        }

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(
                GravityCompat.START)
        }

        return binding.root
    }

    private fun setupViews(locations: List<Location>) {
        val spinnerAdapter = RouteLocationSpinnerAdapter(requireContext(), locations)
        binding.startRouteLocation.adapter = spinnerAdapter

        binding.startRouteLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val locationName = (parent?.getItemAtPosition(position) as Location).name
                Toast.makeText(requireContext(), locationName, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.startRouteLocation.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(requireContext(), "Spinner clicked", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun setupObservers() {
        viewModel.savedLocationsLiveData.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupViews(resource.data!!)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}