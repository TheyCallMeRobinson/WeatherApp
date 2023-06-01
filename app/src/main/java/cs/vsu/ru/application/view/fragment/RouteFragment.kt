package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteBinding
import cs.vsu.ru.application.motion.RouteLocationsTransitionListener
import cs.vsu.ru.application.view.adapter.RouteLocationSpinnerAdapter
import cs.vsu.ru.application.viewmodel.RouteViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class RouteFragment : Fragment() {

    private val viewModel by viewModel<RouteViewModel>()
    private lateinit var binding: FragmentRouteBinding
    private lateinit var motionLayout: MotionLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater)

        motionLayout = binding.root
        motionLayout.setTransitionListener(RouteLocationsTransitionListener())

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
//                motionLayout.setTransition(R.id.half_animation, R.id.end_animation)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.startRouteLocation.setOnClickListener {
            Toast.makeText(requireContext(), "Spinner on click", Toast.LENGTH_LONG).show()
        }
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