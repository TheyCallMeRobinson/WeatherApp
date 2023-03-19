package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class FindLocationsByNameUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationName: String): List<Location>? {
        return locationRepository.findLocationsByName(locationName)
    }
}