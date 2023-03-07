package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class SetFavoriteLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(location: Location): Boolean {
        if (locationRepository.getLocation(location.name) != null) {
            return false
        }

        locationRepository.setFavoriteLocation(location)
        return true
    }
}