package cs.vsu.ru.domain.repository.location

import cs.vsu.ru.domain.model.location.Location

interface LocationRepository {
    fun getSavedLocations(): List<Location>
    fun getLocation(name: String): Location
    fun getFavoriteLocation(): Location
    fun getCurrentLocation(): Location
    fun saveLocation(location: Location): Boolean
    fun setFavoriteLocation(location: Location): Boolean
    fun setCurrentLocation(location: Location): Boolean
    fun removeSavedLocation(locationName: String)
}