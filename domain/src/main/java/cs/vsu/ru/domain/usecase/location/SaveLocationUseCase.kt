package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class SaveLocationUseCase(private val locationRepository: LocationRepository) {

    fun execute(location: Location): Boolean {
        return locationRepository.saveLocation(location)
    }
}