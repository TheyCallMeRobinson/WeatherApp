package cs.vsu.ru.domain.usecase.weather

import cs.vsu.ru.domain.model.weather.Weather

class GetWeatherDataUseCase {

    fun execute(): Weather {
        return Weather()
    }
}