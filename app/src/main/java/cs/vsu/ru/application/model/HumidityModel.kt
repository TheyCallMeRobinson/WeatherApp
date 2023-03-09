package cs.vsu.ru.application.model

import kotlin.math.ceil

class HumidityModel(private val humidity: Float) {

    override fun toString(): String {
        return "${ceil(humidity).toInt()}%"
    }
}