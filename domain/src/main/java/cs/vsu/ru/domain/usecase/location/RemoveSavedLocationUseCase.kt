package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.repository.location.LocationRepository

class RemoveSavedLocationUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(locationName: String) {
        // TODO: if removing current, favorite or last saved location add default location

        if (locationRepository.getSavedLocations().size == 1 ||
            locationRepository.getFavoriteLocation()?.name == locationName ||
            locationRepository.getCurrentLocation()?.name == locationName
        ) {
            throw Exception("gfregerhger")
        }

        locationRepository.removeSavedLocation(locationName)
    }
}