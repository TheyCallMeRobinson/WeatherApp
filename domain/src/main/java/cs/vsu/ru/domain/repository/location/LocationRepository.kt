package cs.vsu.ru.domain.repository.location

import cs.vsu.ru.domain.model.location.Location

interface LocationRepository {
    suspend fun getSavedLocations(): List<Location>
    suspend fun getLocation(name: String): Location?
    suspend fun getFavoriteLocation(): Location?
    suspend fun getCurrentLocation(): Location?
    suspend fun saveLocation(location: Location): Boolean
    suspend fun setFavoriteLocation(location: Location): Boolean
    suspend fun setCurrentLocation(location: Location): Boolean
    suspend fun removeSavedLocation(locationName: String)
}