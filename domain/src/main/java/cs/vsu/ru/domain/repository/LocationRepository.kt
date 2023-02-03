package cs.vsu.ru.domain.repository

import cs.vsu.ru.domain.model.Location

interface LocationRepository {
    fun getLocation(): Location
    fun saveLocation(locationName: String)
}