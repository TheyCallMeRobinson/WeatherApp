package cs.vsu.ru.domain.usecase

import cs.vsu.ru.domain.model.Location

class LoadLocation {

    public fun execute(): Location {
        return Location()
    }
}