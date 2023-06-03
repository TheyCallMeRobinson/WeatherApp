package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.renderscript.ScriptGroup.Input
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
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteWeatherBinding


private const val ZOOM_FACTOR = 1.5f
private const val INITIAL_ZOOM_VALUE = 14.0f
private const val INITIAL_AZIMUTH_VALUE = 0.0f
private const val INITIAL_TILT_VALUE = 0.0f
private const val MAP_MOVE_ANIMATION_DURATION = 0.5f

class RouteWeatherFragment : Fragment() {

    private lateinit var binding: FragmentRouteWeatherBinding
    private lateinit var mapView: MapView

    private val mapInputListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {
            Toast.makeText(context, "${p1.latitude}, ${p1.longitude}", Toast.LENGTH_LONG).show()
        }

        override fun onMapLongTap(p0: Map, p1: Point) {}
    }

    private val startTravelPoint: Point? = null
    private val endTravelPoint: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteWeatherBinding.inflate(inflater)

        MapKitFactory.initialize(requireContext())
        mapView = binding.fragmentRouteMapkit
        mapView.map.move(
            CameraPosition(
                Point(59.945933, 30.320045),
                INITIAL_ZOOM_VALUE,
                INITIAL_AZIMUTH_VALUE,
                INITIAL_TILT_VALUE
            ),
        )
        mapView.map.isNightModeEnabled = true

        binding.toFragmentMainBtn.setOnClickListener {
            navigateBack()
        }

        mapView.map.addInputListener(mapInputListener)

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(
                GravityCompat.START
            )
        }

        binding.zoomInButton.setOnClickListener {
            zoomIn()
        }

        binding.zoomOutButton.setOnClickListener {
            zoomOut()
        }

        binding.centerAzimuthButton.setOnClickListener {
            centerAzimuth()
        }

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