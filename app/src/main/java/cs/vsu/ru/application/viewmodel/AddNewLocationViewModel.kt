package cs.vsu.ru.application.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.environment.Resource
import kotlinx.coroutines.Dispatchers

@Suppress("DEPRECATION")
class AddNewLocationViewModel(
) : ViewModel() {

    fun getLocationsByName(context: Context, locationName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocationName(locationName, 10) // TODO: maxResults should be getting from app settings
            val locations = addresses?.map {
                Location(
                    name = locationName,
                    country = it.countryName,
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            }
            emit(Resource.success(data = locations))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = "Failed while geocoding location"))
        }
    }
}