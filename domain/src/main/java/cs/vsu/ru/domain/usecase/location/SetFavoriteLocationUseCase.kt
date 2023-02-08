package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class SetFavoriteLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(location: Location): Boolean {
        return locationRepository.setFavoriteLocation(location)
    }
}