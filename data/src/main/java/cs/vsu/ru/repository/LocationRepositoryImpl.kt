package cs.vsu.ru.repository

import android.content.Context
import android.location.Geocoder
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository
import cs.vsu.ru.mapper.LocationMapper

private const val SHARED_PREFERENCES_NAME = "weather_application_shared_preferences"
private const val KEY_FAVORITE_LOCATION = "favoriteLocation"
private const val KEY_CURRENT_LOCATION = "currentLocation"
private const val DEFAULT_LOCATION = "Moscow"

// Suppression should stay here as API 33 deprecates Geocoder.getFromLocationName and
// suggests to use newer method instead
// But this method does not supported by API < 33, that makes backward compatibility impossible
@Suppress("DEPRECATION")
open class LocationRepositoryImpl(
    private val locationDao: LocationDao,
    private val context: Context) : LocationRepository {

    private val locationMapper = LocationMapper()
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override suspend fun getSavedLocations(): List<Location> {
        return locationDao.getAll()!!
            .map{locationEntity -> locationMapper.dataToDomain(locationEntity)}
            .toList()
    }

    override suspend fun getLocation(name: String): Location {
        return locationMapper.dataToDomain(locationDao.getByName(name))
    }

    override suspend fun getFavoriteLocation(): Location {
        return getLocation(sharedPreferences.getString(KEY_FAVORITE_LOCATION, DEFAULT_LOCATION)!!)
    }

    override suspend fun getCurrentLocation(): Location {
        return getLocation(sharedPreferences.getString(KEY_CURRENT_LOCATION, DEFAULT_LOCATION)!!)
    }

    override suspend fun saveLocation(location: Location): Boolean {
        if (locationDao.getByName(location.name) != null) {
            return false
        }
        locationDao.insert(locationMapper.domainToData(location))
        return true
    }

    override suspend fun setFavoriteLocation(location: Location): Boolean {
        sharedPreferences.edit().putString(KEY_FAVORITE_LOCATION, location.name).apply()
        return true
    }

    override suspend fun setCurrentLocation(location: Location): Boolean {
        if (locationDao.getByName(KEY_CURRENT_LOCATION) == null) {
            return false
        }
        sharedPreferences.edit().putString(KEY_CURRENT_LOCATION, location.name).apply()
        return true
    }

    override suspend fun removeSavedLocation(locationName: String) {
        locationDao.deleteByName(locationName)
    }

    override suspend fun findLocationsByName(locationName: String): List<Location>? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(locationName, 10) // TODO: maxResults should be getting from app settings
        val locations = addresses?.map {
            Location(
                name = it.locality,
                country = it.countryName,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }

        return locations
    }
}