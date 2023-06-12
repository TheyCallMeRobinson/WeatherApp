package cs.vsu.ru.application.view.controller

import android.content.Context
import android.util.Log
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.*
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import cs.vsu.ru.domain.model.location.Location


private const val ZOOM_FACTOR = 1.5f
private const val INITIAL_ZOOM_VALUE = 14.0f
private const val INITIAL_AZIMUTH_VALUE = 0.0f
private const val INITIAL_TILT_VALUE = 0.0f
private const val MAP_MOVE_ANIMATION_DURATION = 0.5f

class YandexMapController(mapView: MapView, context: Context) {

    private val mapView: MapView
    private val drivingRouter: DrivingRouter
    private lateinit var mapObjects: MapObjectCollection
    private var drivingSession: DrivingSession? = null

    private val drivingRouteListener = object : DrivingSession.DrivingRouteListener {
        override fun onDrivingRoutes(routes: List<DrivingRoute>) {
            for (route in routes) {
                mapObjects.addPolyline(route.geometry)
            }
        }

        override fun onDrivingRoutesError(p0: Error) {
            var errorMessage = "Неизвестная ошибка"
            if (p0 is RemoteError) {
                errorMessage = "Ошибка сервера"
            } else if (p0 is NetworkError) {
                errorMessage = "Ошибка сети"
            }
            Log.e("MapKit Route", errorMessage)
        }

    }

    init {
        MapKitFactory.initialize(context)
        DirectionsFactory.initialize(context);
        this.mapView = mapView
        this.mapView.map.isNightModeEnabled = true
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()
    }

    fun mapOnStart() {
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun mapOnStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    fun moveMapTo(latitude: Double, longitude: Double) {
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

    fun centerAzimuth() {
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

    fun zoomIn() {
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

    fun zoomOut() {
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

    fun addInputListener(inputListener: InputListener) {
        mapView.map.addInputListener(inputListener)
    }

    fun removeInputListener(inputListener: InputListener) {
        mapView.map.removeInputListener(inputListener)
    }

    fun setNoninteractive(flag: Boolean) {
        mapView.setNoninteractive(flag)
    }

    fun showRoute(start: Location, end: Location) {
        clearCurrentRoute()
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        val startPoint = Point(start.latitude!!, start.longitude!!)
        val endPoint = Point(end.latitude!!, end.longitude!!)
        val requestPoints = arrayListOf(
            RequestPoint(startPoint, RequestPointType.WAYPOINT, null),
            RequestPoint(endPoint, RequestPointType.WAYPOINT, null)
        )

        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, drivingRouteListener)
        moveCameraOnStart(startPoint)
    }

    private fun clearCurrentRoute() {
        mapObjects.clear()
    }

    private fun moveCameraOnStart(startPoint: Point) {
        mapView.map.move(
            CameraPosition(
                startPoint,
                mapView.map.cameraPosition.zoom,
                INITIAL_AZIMUTH_VALUE,
                INITIAL_TILT_VALUE
            ),
            Animation(Animation.Type.SMOOTH, MAP_MOVE_ANIMATION_DURATION),
            null
        )

    }
}