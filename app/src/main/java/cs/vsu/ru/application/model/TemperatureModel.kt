package cs.vsu.ru.application.model

import kotlin.math.ceil

class TemperatureModel(private val temperatureInKelvin: Float) {

    override fun toString(): String {
        return "${ceil(temperatureInKelvin).toInt()}°"
    }
}