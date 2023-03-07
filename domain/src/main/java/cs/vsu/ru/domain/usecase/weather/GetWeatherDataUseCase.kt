package cs.vsu.ru.domain.usecase.weather

import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository

class GetWeatherDataUseCase(val repository: WeatherRepository) {

    fun execute(): Weather {
        return repository.getWeather()
    }
}