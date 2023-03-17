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

open class LocationRepositoryImpl(
    private val locationDao: LocationDao,
    context: Context) : LocationRepository {

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
        if (locationDao.getByName(KEY_FAVORITE_LOCATION) == null) {
            return false
        }
        sharedPreferences.edit().putString(KEY_FAVORITE_LOCATION, location.name).apply()
        return true
        // return setLocationIntoSharedPreferences(KEY_FAVORITE_LOCATION, location.name)
    }

    override suspend fun setCurrentLocation(location: Location): Boolean {
        if (locationDao.getByName(KEY_CURRENT_LOCATION) == null) {
            return false
        }
        sharedPreferences.edit().putString(KEY_CURRENT_LOCATION, location.name).apply()
        return true
        // return setLocationIntoSharedPreferences(KEY_CURRENT_LOCATION, location.name)
    }

    override suspend fun removeSavedLocation(locationName: String) {
        locationDao.deleteByName(locationName)
    }

    private suspend fun setLocationIntoSharedPreferences(key: String, value: String): Boolean {
        if (locationDao.getByName(value) == null) {
            return false
        }
        sharedPreferences.edit().putString(key, value).apply()
        return true
    }

}