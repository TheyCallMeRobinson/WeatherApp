package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteWeatherBinding


class RouteWeatherFragment : Fragment() {

    private lateinit var binding: FragmentRouteWeatherBinding
    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteWeatherBinding.inflate(inflater)

        MapKitFactory.initialize(requireContext())
        mapView = binding.fragmentRouteMapkit
        mapView.map.move(
            CameraPosition(Point(59.945933, 30.320045), 14.0f, 0.0f, 0.0f),
        )
        mapView.map.isNightModeEnabled = true

        binding.toFragmentMainBtn.setOnClickListener {
            navigateBack()
        }

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(
                GravityCompat.START)
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

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}