package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class SetCurrentLocationUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(location: Location): Boolean {
        return locationRepository.setCurrentLocation(location)
    }
}