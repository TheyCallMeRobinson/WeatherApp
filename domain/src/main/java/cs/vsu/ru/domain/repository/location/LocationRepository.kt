package cs.vsu.ru.domain.repository.location

import cs.vsu.ru.domain.model.location.Location

interface LocationRepository {
    suspend fun getSavedLocations(): List<Location>
    suspend fun getLocation(name: String): Location?
    suspend fun getFavoriteLocation(): Location
    suspend fun getCurrentLocation(): Location?
    suspend fun saveLocation(location: Location)
    suspend fun setFavoriteLocation(location: Location)
    suspend fun setCurrentLocation(location: Location)
    suspend fun removeSavedLocation(locationName: String)
    suspend fun findLocationsByName(locationName: String): List<Location>?
    suspend fun findLocationByCoordinates(latitude: Double, longitude: Double): Location?
}