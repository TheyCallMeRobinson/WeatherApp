package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteWeatherBinding
import cs.vsu.ru.application.view.adapter.NewLocationsListAdapter
import cs.vsu.ru.application.view.controller.YandexMapController
import cs.vsu.ru.application.view.stateholder.RouteMapState
import cs.vsu.ru.application.view.stateholder.RouteMenuState
import cs.vsu.ru.application.view.stateholder.RouteViewState
import cs.vsu.ru.application.viewmodel.RouteWeatherViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class RouteWeatherFragment : Fragment() {

    private val viewModel by viewModel<RouteWeatherViewModel>()
    private lateinit var mapController: YandexMapController
    private lateinit var binding: FragmentRouteWeatherBinding

    private val mapStartLocationInputListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {
            Toast.makeText(context, "Start: ${p1.latitude}, ${p1.longitude}", Toast.LENGTH_LONG).show()
            viewModel.setStartLocation(p1.latitude, p1.longitude)
        }

        override fun onMapLongTap(p0: Map, p1: Point) {}
    }

    private val mapEndLocationInputListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {
            Toast.makeText(context, "End: ${p1.latitude}, ${p1.longitude}", Toast.LENGTH_LONG).show()
            viewModel.setEndLocation(p1.latitude, p1.longitude)
        }

        override fun onMapLongTap(p0: Map, p1: Point) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteWeatherBinding.inflate(inflater)

        setupMap()
        setupViews()
        setupButtons()
        setupObservers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mapController.mapOnStart()
    }

    override fun onStop() {
        mapController.mapOnStop()
        super.onStop()
    }

    private fun setupViews() {
        binding.routeLocationManagerContainer.startRouteLocationEt.doOnTextChanged { text, _, _, _ ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        viewModel.currentViewState.observe(viewLifecycleOwner) {
            when(it) {
                RouteViewState.OPEN_MAP -> {
                    binding.root.transitionToState(R.id.collapsed_menu)
                    mapController.setNoninteractive(false)
                }
                else -> {
                    binding.root.transitionToState(R.id.expanded_menu)
                    mapController.setNoninteractive(true)
                }
            }
        }
        viewModel.currentMapState.observe(viewLifecycleOwner) {
            when(it) {
                RouteMapState.OBSERVE_MAP -> {
                    mapController.removeInputListener(mapStartLocationInputListener)
                    mapController.removeInputListener(mapEndLocationInputListener)
                }
                RouteMapState.OBSERVE_MAP_FOR_END_LOCATION -> {
                    mapController.removeInputListener(mapStartLocationInputListener)
                    mapController.addInputListener(mapEndLocationInputListener)
                }
                RouteMapState.OBSERVE_MAP_FOR_START_LOCATION -> {
                    mapController.addInputListener(mapStartLocationInputListener)
                    mapController.removeInputListener(mapEndLocationInputListener)
                }
                RouteMapState.OBSERVE_MAP_FOR_ROUTE -> {}
                else -> {}
            }
        }
        viewModel.currentMenuState.observe(viewLifecycleOwner) {
            when(it) {
                RouteMenuState.IDLE -> {}
                RouteMenuState.CHOOSING_START_LOCATION -> {}
                RouteMenuState.CHOOSING_END_LOCATION -> {}
                RouteMenuState.SHOWING_ROUTE_WEATHER_DATA -> {}
            }
        }
        viewModel.getCurrentLocation().observe(viewLifecycleOwner) {
            when(it.status) {
                Status.LOADING -> {
                    binding.mapProgressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.mapProgressBar.visibility = View.INVISIBLE
                    mapController.moveMapTo(it.data?.latitude!!, it.data?.longitude!!)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Не удалось определить местоположение", Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.savedLocations.observe(viewLifecycleOwner) {
            showSavedLocations(it)
        }
        viewModel.startLocation.observe(viewLifecycleOwner) {
            it?.let {
                binding.routeLocationManagerContainer.startRouteLocationEt.setText(it.name)
            }
        }
        viewModel.endLocation.observe(viewLifecycleOwner) {
            it?.let {
                binding.routeLocationManagerContainer.endRouteLocationEt.setText(it.name)
            }
        }
    }

    private fun setupButtons() {
        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(
                GravityCompat.START
            )
        }

        binding.toFragmentMainBtn.setOnClickListener { navigateBack() }

        binding.mapControlPanelContainer.zoomInButton.setOnClickListener { mapController.zoomIn() }

        binding.mapControlPanelContainer.zoomOutButton.setOnClickListener { mapController.zoomOut() }

        binding.mapControlPanelContainer.centerAzimuthButton.setOnClickListener { mapController.centerAzimuth() }

        binding.toggleLocationManagerMenuBtn.setOnClickListener {
            when(viewModel.currentViewState.value) {
                RouteViewState.OPEN_MAP -> {
                    viewModel.currentViewState.value = RouteViewState.OPEN_MENU
                }
                else -> {
                    viewModel.currentViewState.value = RouteViewState.OPEN_MAP
                }
            }
        }

        binding.routeLocationManagerContainer.startRoutePinImg.setOnClickListener {
            viewModel.currentViewState.value = RouteViewState.OPEN_MAP
            viewModel.currentMapState.value = RouteMapState.OBSERVE_MAP_FOR_START_LOCATION
        }

        binding.routeLocationManagerContainer.endRoutePinImg.setOnClickListener {
            viewModel.currentViewState.value = RouteViewState.OPEN_MAP
            viewModel.currentMapState.value = RouteMapState.OBSERVE_MAP_FOR_END_LOCATION
        }

        binding.routeLocationManagerContainer.getRouteButton.setOnClickListener {
            mapController.showRoute(viewModel.startLocation.value!!, viewModel.endLocation.value!!)
        }
    }

    private fun setupMap() {
        mapController = YandexMapController(binding.fragmentRouteMapkit, requireContext())
    }

    private fun showSavedLocations(savedLocations: List<Location>) {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val listAdapter = NewLocationsListAdapter(newLocations = savedLocations) {
            viewModel.setLocationFromList(it)
        }
        binding.routeLocationManagerContainer.locationsDataManagerList.apply {
            layoutManager = linearLayoutManager
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}
