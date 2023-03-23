package cs.vsu.ru.application.viewmodel

import android.util.Log
import androidx.lifecycle.*
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.GetFavoriteLocationUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase
import cs.vsu.ru.domain.usecase.location.RemoveSavedLocationUseCase
import cs.vsu.ru.domain.usecase.location.SetFavoriteLocationUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrawerViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getFavoriteLocationUseCase: GetFavoriteLocationUseCase,
    private val setFavoriteLocationUseCase: SetFavoriteLocationUseCase,
    private val removeSavedLocationUseCase: RemoveSavedLocationUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val favoriteLocation = MutableLiveData<Location>()
    val favoriteLocationLiveData: LiveData<Location> = favoriteLocation

    private val savedLocations = MutableLiveData<Resource<List<Location>>>()
    val savedLocationsLiveData: LiveData<Resource<List<Location>>> = savedLocations

    init {
        viewModelScope.launch {
            val result = getFavoriteLocationUseCase.execute()
            favoriteLocation.value = result
        }
        savedLocations.value = getSavedLocations().value
    }

    fun getFavoriteLocation() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getFavoriteLocationUseCase.execute()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Не удалось загрузить избранное местоположение"))
        }
    }

    fun getSavedLocations() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getSavedLocationsUseCase.execute()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Не удалось загрузить сохраненные местоположения"))
        }
    }

    fun setFavoriteLocation(location: Location): Location? {
        val previousFavoriteLocation = favoriteLocation.value
        viewModelScope.launch {
            setFavoriteLocationUseCase.execute(location)
            favoriteLocation.value = getFavoriteLocationUseCase.execute()
        }
        savedLocations.value = getSavedLocations().value
        return previousFavoriteLocation
    }

    fun removeSavedLocation(location: Location) = scope.launch {
        try {
            removeSavedLocationUseCase.execute(location.name)
            savedLocations.value = getSavedLocations().value
        } catch (exception: Exception) {
            Log.e("Drawer", exception.message!!)
        }
    }
}
