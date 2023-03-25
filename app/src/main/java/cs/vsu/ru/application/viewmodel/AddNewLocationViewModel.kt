package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.FindLocationsByNameUseCase
import cs.vsu.ru.domain.usecase.location.SaveLocationUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.*

class AddNewLocationViewModel(
    private val findLocationsByNameUseCase: FindLocationsByNameUseCase,
    private val saveLocationUseCase: SaveLocationUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun getLocationsByName(locationName: String) = liveData(Dispatchers.IO) {
        if (locationName.trim().isEmpty()) {
            emit(Resource.error(data = null, message = "Это поле не может быть пустым"))
            return@liveData
        }

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = findLocationsByNameUseCase.execute(locationName)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Неправильно введены данные"))
        }
    }

    fun saveLocation(location: Location) {
        scope.launch {
            saveLocationUseCase.execute(location)
        }
    }
}
