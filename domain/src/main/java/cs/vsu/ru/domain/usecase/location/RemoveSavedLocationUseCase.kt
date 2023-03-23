package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.repository.location.LocationRepository

class RemoveSavedLocationUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationName: String) {
        locationRepository.removeSavedLocation(locationName)
    }
}