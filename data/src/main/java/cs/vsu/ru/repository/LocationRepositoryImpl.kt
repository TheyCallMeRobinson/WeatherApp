package cs.vsu.ru.repository

import android.content.Context
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository
import cs.vsu.ru.mapper.LocationMapper

private const val SHARED_PREFERENCES_NAME = "weather_application_shared_preferences"
private const val KEY_FAVORITE_LOCATION = "favoriteLocation"
private const val KEY_CURRENT_LOCATION = "currentLocation"
private const val DEFAULT_LOCATION = "Moscow"

class LocationRepositoryImpl(
    private val locationDao: LocationDao,
    private val context: Context) : LocationRepository {

    private val locationMapper = LocationMapper()
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun getSavedLocations(): List<Location> {
        return locationDao.getAll()!!
            .map{locationEntity -> locationMapper.dataToDomain(locationEntity)}
            .toList()
    }

    override fun getLocation(name: String): Location {
        return locationMapper.dataToDomain(locationDao.getByName(name))
    }

    override fun getFavoriteLocation(): Location {
        // TODO: if favorite location is not presented - return default location
        return getLocation(sharedPreferences.getString(KEY_FAVORITE_LOCATION, DEFAULT_LOCATION)!!)
    }

    override fun getCurrentLocation(): Location {
        // TODO: if current location is not presented - return default location
        return getLocation(sharedPreferences.getString(KEY_CURRENT_LOCATION, DEFAULT_LOCATION)!!)
    }

    override fun saveLocation(location: Location): Boolean {
        if (locationDao.getByName(location.name) != null) {
            return false
        }
        locationDao.insert(locationMapper.domainToData(location))
        return true
    }

    override fun setFavoriteLocation(location: Location): Boolean {
        return setLocationIntoSharedPreferences(KEY_FAVORITE_LOCATION, location.name)
    }

    override fun setCurrentLocation(location: Location): Boolean {
        return setLocationIntoSharedPreferences(KEY_CURRENT_LOCATION, location.name)
    }

    private fun setLocationIntoSharedPreferences(key: String, value: String): Boolean {
        if (locationDao.getByName(value) == null) {
            return false
        }
        sharedPreferences.edit().putString(key, value).apply()
        return true
    }

    override fun removeSavedLocation(locationName: String) {
        // TODO: if removing current, favorite or last saved location add default location
        locationDao.deleteByName(locationName)
    }
}