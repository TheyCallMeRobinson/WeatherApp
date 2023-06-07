package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cs.vsu.ru.application.view.stateholder.RouteMapState
import cs.vsu.ru.application.view.stateholder.RouteMenuState
import cs.vsu.ru.application.view.stateholder.RouteViewState
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.FindLocationByCoordinatesUseCase
import cs.vsu.ru.domain.usecase.location.FindLocationsByNameUseCase
import cs.vsu.ru.domain.usecase.location.GetCurrentLocationUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteWeatherViewModel(
    private val findLocationByCoordinatesUseCase: FindLocationByCoordinatesUseCase,
    private val findLocationsByNameUseCase: FindLocationsByNameUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase
) : ViewModel() {

    val currentMapState = MutableLiveData<RouteMapState>()
    val currentMenuState = MutableLiveData<RouteMenuState>()
    val currentViewState = MutableLiveData<RouteViewState>()

    private val startLocationMutable = MutableLiveData<Location?>()
    val startLocation: LiveData<Location?> = startLocationMutable

    private val endLocationMutable = MutableLiveData<Location?>()
    val endLocation: LiveData<Location?> = endLocationMutable

    init {
        currentMapState.value = RouteMapState.OBSERVE_MAP
        currentMenuState.value = RouteMenuState.IDLE
        currentViewState.value = RouteViewState.OPEN_MAP
    }

    fun getCurrentLocation() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getCurrentLocationUseCase.execute()))
        } catch(exception: Exception) {
            emit(Resource.error(data = null, message = exception.message!!))
        }
    }

    fun getSavedLocations() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getSavedLocationsUseCase.execute()))
        } catch(exception: Exception) {
            emit(Resource.error(data = null, message = exception.message!!))
        }
    }

    fun findLocationsByName(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = findLocationsByNameUseCase.execute(name)))
        } catch(exception: Exception) {
            emit(Resource.error(data = null, message = exception.message!!))
        }
    }

    fun setStartLocation(latitude: Double, longitude: Double) {
        startLocationMutable.value = getLocationByCoordinates(latitude, longitude)
    }

    fun setEndLocation(latitude: Double, longitude: Double) {
        endLocationMutable.value = getLocationByCoordinates(latitude, longitude)
    }

    private fun getLocationByCoordinates(latitude: Double, longitude: Double): Location? {
        var location: Location? = null
        viewModelScope.launch {
            location = findLocationByCoordinatesUseCase.execute(latitude, longitude)
        }
        return location
    }

}