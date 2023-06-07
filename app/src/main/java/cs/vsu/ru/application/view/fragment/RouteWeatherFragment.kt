package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteWeatherBinding
import cs.vsu.ru.application.view.stateholder.RouteMapState
import cs.vsu.ru.application.view.stateholder.RouteMenuState
import cs.vsu.ru.application.view.stateholder.RouteViewState
import cs.vsu.ru.application.viewmodel.RouteWeatherViewModel
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val ZOOM_FACTOR = 1.5f
private const val INITIAL_ZOOM_VALUE = 14.0f
private const val INITIAL_AZIMUTH_VALUE = 0.0f
private const val INITIAL_TILT_VALUE = 0.0f
private const val MAP_MOVE_ANIMATION_DURATION = 0.5f

class RouteWeatherFragment : Fragment() {

    private val viewModel by viewModel<RouteWeatherViewModel>()
    private lateinit var binding: FragmentRouteWeatherBinding
    private lateinit var mapView: MapView

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
        setupButtons()
        setupObservers()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun setupObservers() {
        viewModel.currentViewState.observe(viewLifecycleOwner) {
            when(it) {
                RouteViewState.OPEN_MAP -> {
                    binding.root.transitionToState(R.id.collapsed_menu)
                    mapView.setNoninteractive(false)
                }
                else -> {
                    binding.root.transitionToState(R.id.expanded_menu)
                    mapView.setNoninteractive(true)
                }
            }
        }
        viewModel.currentMapState.observe(viewLifecycleOwner) {
            when(it) {
                RouteMapState.OBSERVE_MAP -> {
                    mapView.map.removeInputListener(mapStartLocationInputListener)
                    mapView.map.removeInputListener(mapEndLocationInputListener)
                }
                RouteMapState.OBSERVE_MAP_FOR_END_LOCATION -> {
                    mapView.map.removeInputListener(mapStartLocationInputListener)
                    mapView.map.addInputListener(mapEndLocationInputListener)
                }
                RouteMapState.OBSERVE_MAP_FOR_START_LOCATION -> {
                    mapView.map.addInputListener(mapStartLocationInputListener)
                    mapView.map.removeInputListener(mapEndLocationInputListener)
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
                    moveMapTo(it.data?.latitude!!, it.data?.longitude!!)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Не удалось определить местоположение", Toast.LENGTH_LONG).show()
                }
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

        binding.mapControlPanelContainer.zoomInButton.setOnClickListener { zoomIn() }

        binding.mapControlPanelContainer.zoomOutButton.setOnClickListener { zoomOut() }

        binding.mapControlPanelContainer.centerAzimuthButton.setOnClickListener { centerAzimuth() }

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
    }

    private fun setupMap() {
        MapKitFactory.initialize(requireContext())
        mapView = binding.fragmentRouteMapkit
        mapView.map.isNightModeEnabled = true
    }

    private fun moveMapTo(latitude: Double, longitude: Double) {
        mapView.map.move(
            CameraPosition(
                Point(latitude, longitude),
                INITIAL_ZOOM_VALUE,
                0.0f,
                0.0f
            ),
            Animation(Animation.Type.SMOOTH, MAP_MOVE_ANIMATION_DURATION),
            null
        )
    }

    private fun centerAzimuth() {
        mapView.map.move(
            CameraPosition(
                mapView.map.cameraPosition.target,
                mapView.map.cameraPosition.zoom,
                0.0f,
                0.0f
            ),
            Animation(Animation.Type.SMOOTH, MAP_MOVE_ANIMATION_DURATION),
            null
        )
    }

    private fun zoomIn() {
        mapView.map.move(
            CameraPosition(
                mapView.map.cameraPosition.target,
                mapView.map.cameraPosition.zoom + ZOOM_FACTOR,
                mapView.map.cameraPosition.azimuth,
                mapView.map.cameraPosition.tilt
            ),
            Animation(Animation.Type.SMOOTH, MAP_MOVE_ANIMATION_DURATION),
            null
        )
    }

    private fun zoomOut() {
        mapView.map.move(
            CameraPosition(
                mapView.map.cameraPosition.target,
                mapView.map.cameraPosition.zoom - ZOOM_FACTOR,
                mapView.map.cameraPosition.azimuth,
                mapView.map.cameraPosition.tilt
            ),
            Animation(Animation.Type.SMOOTH, MAP_MOVE_ANIMATION_DURATION),
            null
        )
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}