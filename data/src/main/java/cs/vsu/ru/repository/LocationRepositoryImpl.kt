package cs.vsu.ru.repository

import android.content.Context
import android.location.Address
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

    override suspend fun saveLocation(location: Location) {
        if (locationDao.getByName(location.name) != null) {
            return
        }
        locationDao.insert(locationMapper.domainToData(location))
    }

    override suspend fun setFavoriteLocation(location: Location) {
        sharedPreferences.edit().putString(KEY_FAVORITE_LOCATION, location.name).apply()
    }

    override suspend fun setCurrentLocation(location: Location) {
        sharedPreferences.edit().putString(KEY_CURRENT_LOCATION, location.name).apply()
    }

    override suspend fun removeSavedLocation(locationName: String) {
        locationDao.deleteByName(locationName)
    }

    override suspend fun findLocationsByName(locationName: String): List<Location>? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(locationName, 10) // TODO: maxResults should be getting from app settings

        return parseAddresses(addresses)
    }

    override suspend fun findLocationByCoordinates(latitude: Double, longitude: Double): Location? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(latitude, longitude, 10)

        return parseAddresses(addresses)?.get(0)
    }

    private fun parseAddresses(addresses: List<Address>?): List<Location>? {
        val locations = addresses?.map {
            var name: String
            if (it.featureName == null) {
                name = it.locality
            }
            if (it.thoroughfare == null || it.featureName == it.thoroughfare) {
                name = it.featureName
            } else {
                name = "${it.thoroughfare}, ${it.featureName}"
            }

            Location(
                name = name,
                country = it.countryName,
                adminArea = it.adminArea,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
        return locations
    }

}