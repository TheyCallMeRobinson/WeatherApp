package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.repository.location.LocationRepository

class RemoveSavedLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(locationName: String) {
        locationRepository.removeSavedLocation(locationName)
    }
}