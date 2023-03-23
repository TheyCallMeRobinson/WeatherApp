package cs.vsu.ru.domain.usecase.location

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository

class SaveLocationUseCase(private val locationRepository: LocationRepository) {

    suspend fun execute(location: Location) {
        locationRepository.saveLocation(location)
    }
}