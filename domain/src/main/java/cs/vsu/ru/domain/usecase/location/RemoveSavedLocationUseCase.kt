package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class RemoveSavedLocationUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationName: String) {
        if (locationRepository.getSavedLocations().size == 1 ||
            locationRepository.getFavoriteLocation()?.name == locationName ||
            locationRepository.getCurrentLocation()?.name == locationName
        ) {
            locationRepository.saveLocation(Location())
        }

        locationRepository.removeSavedLocation(locationName)
    }
}