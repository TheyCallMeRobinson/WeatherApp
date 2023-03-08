package cs.vsu.ru.mapper

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.model.location.LocationEntity

class LocationMapper {

    fun domainToData(fromDomain: Location?): LocationEntity {
        if (fromDomain?.name == null) {
            TODO("throw custom exception")
        }

        return LocationEntity(
            name = fromDomain.name,
            country = fromDomain.country,
            latitude = fromDomain.latitude,
            longitude = fromDomain.longitude
        )
    }

    fun dataToDomain(fromData: LocationEntity?): Location {
        if (fromData?.name == null) {
            return Location(
                name = "Воронеж",
                country = "Россия",
                latitude = 51.672,
                longitude = 39.1843
            )
        }

        return Location(
            name = fromData.name,
            country = fromData.country,
            latitude = fromData.latitude,
            longitude = fromData.longitude
        )
    }
}