package cs.vsu.ru.application.viewmodel

import android.util.Log
import androidx.lifecycle.*
import cs.vsu.ru.application.motion.SavedLocationsTransitionListener
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.GetFavoriteLocationUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase
import cs.vsu.ru.domain.usecase.location.RemoveSavedLocationUseCase
import cs.vsu.ru.domain.usecase.location.SetFavoriteLocationUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.getScopeId

class DrawerViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getFavoriteLocationUseCase: GetFavoriteLocationUseCase,
    private val setFavoriteLocationUseCase: SetFavoriteLocationUseCase,
    private val removeSavedLocationUseCase: RemoveSavedLocationUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val favoriteLocation = MutableLiveData<Resource<Location>>()
    val favoriteLocationLiveData: LiveData<Resource<Location>> = favoriteLocation

    private val savedLocations = MutableLiveData<Resource<List<Location>>>()
    val savedLocationsLiveData: LiveData<Resource<List<Location>>> = savedLocations

    // God forgive me for what am I about to do
    // ToDo: Overhaul to data flow
    fun refreshData() {
        getSavedLocations().observeForever {
            savedLocations.value = it
        }
        getFavoriteLocation().observeForever {
            favoriteLocation.value = it
        }
    }

    private fun getFavoriteLocation() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getFavoriteLocationUseCase.execute()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Не удалось загрузить избранное местоположение"))
        }
    }

    private fun getSavedLocations() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getSavedLocationsUseCase.execute()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Не удалось загрузить сохраненные местоположения"))
        }
    }

    fun setFavoriteLocation(location: Location) {
        scope.launch {
            try {
                setFavoriteLocationUseCase.execute(location)
            } catch (exception: Exception) {
                Log.e("Drawer fav location", exception.message ?: "Couldn't set location as favorite")
            }
        }
        refreshData()
    }

    fun removeSavedLocation(location: Location) {
        scope.launch {
            try {
                removeSavedLocationUseCase.execute(location.name)
            } catch (exception: Exception) {
                Log.e("Drawer remove location", exception.message ?: "Couldn't remove saved location")
            }
        }
        refreshData()
    }

    // ToDo: Return this to DrawerFragment
    fun onRemoveTransitionListener(location: Location): SavedLocationsTransitionListener =
        SavedLocationsTransitionListener { removeSavedLocation(location) }

    fun onFavoriteTransitionListener(location: Location): SavedLocationsTransitionListener =
        SavedLocationsTransitionListener { setFavoriteLocation(location) }
}
