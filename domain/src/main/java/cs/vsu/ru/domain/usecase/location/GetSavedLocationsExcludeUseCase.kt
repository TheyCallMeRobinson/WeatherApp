package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class GetSavedLocationsExcludeUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(exceptLocation: Location): List<Location> {
        val savedLocations = locationRepository.getSavedLocations().toMutableList()
        savedLocations.remove(exceptLocation)

        return savedLocations
    }
}