package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class GetFavoriteLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(): Location {
        return locationRepository.getFavoriteLocation() ?: Location()
    }
}