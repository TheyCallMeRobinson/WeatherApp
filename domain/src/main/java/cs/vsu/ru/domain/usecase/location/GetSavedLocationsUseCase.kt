package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class GetSavedLocationsUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(): List<Location> {
        return locationRepository.getSavedLocations()
    }
}