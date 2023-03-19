package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.domain.usecase.location.FindLocationsByNameUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers

@Suppress("DEPRECATION")
class AddNewLocationViewModel(
    private val findLocationsByNameUseCase: FindLocationsByNameUseCase
) : ViewModel() {

    fun getLocationsByName(locationName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = findLocationsByNameUseCase.execute(locationName)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Failed while geocoding location"))
        }
    }
}