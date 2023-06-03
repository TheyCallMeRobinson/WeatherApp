package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsExcludeUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsExcludeFavoriteUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase
import cs.vsu.ru.domain.usecase.weather.GetWeatherDataHourlyUseCase
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers

class RouteViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getSavedLocationsExcludeUseCase: GetSavedLocationsExcludeUseCase,
    private val getWeatherDataHourlyUseCase: GetWeatherDataHourlyUseCase
) : ViewModel() {

    private val savedLocations = MutableLiveData<Resource<List<Location>>>()
    val savedLocationsLiveData: LiveData<Resource<List<Location>>> = savedLocations

    private val savedLocationsExclude = MutableLiveData<Resource<List<Location>>>()
    val savedLocationsExcludeLiveData: LiveData<Resource<List<Location>>> = savedLocationsExclude

    fun refreshData() {
        getSavedLocations().observeForever {
            savedLocations.value = it
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

    private fun getSavedLocationsExclude(location: Location) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getSavedLocationsExcludeUseCase.execute(location)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Не удалось загрузить сохраненные местоположения"))
        }
    }

    /*
        getAccounts.enqueue(
            onResponse() {
                ...
                getAccountsData.enqueue() {

                }
            }
        )
     */
}