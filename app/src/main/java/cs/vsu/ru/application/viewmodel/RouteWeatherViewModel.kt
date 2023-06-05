package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cs.vsu.ru.application.stateholder.RouteMapState
import cs.vsu.ru.domain.model.location.Location

class RouteWeatherViewModel : ViewModel() {

    private val currentState = MutableLiveData<RouteMapState>()
    val currentStateLiveData: LiveData<RouteMapState> = currentState

    private val startLocation = MutableLiveData<Location?>()
    val startLocationLiveData: LiveData<Location?> = startLocation

    private val endLocation = MutableLiveData<Location?>()
    val endLocationLiveData: LiveData<Location?> = endLocation



    fun openMenu() {
        currentState.value = RouteMapState.OPEN_MENU
    }

    fun closeMenu() {
        currentState.value = RouteMapState.OBSERVE_MAP
    }

}