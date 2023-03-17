package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.GetFavoriteLocationUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase
import cs.vsu.ru.domain.usecase.location.RemoveSavedLocationUseCase
import cs.vsu.ru.domain.usecase.location.SetFavoriteLocationUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers

class DrawerViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getFavoriteLocationUseCase: GetFavoriteLocationUseCase,
    private val setFavoriteLocationUseCase: SetFavoriteLocationUseCase,
    private val removeSavedLocationUseCase: RemoveSavedLocationUseCase
) : ViewModel() {

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
            emit(Resource.error(data = null, message = "Не удалось загрузить избранное местоположение"))
        }
    }
}