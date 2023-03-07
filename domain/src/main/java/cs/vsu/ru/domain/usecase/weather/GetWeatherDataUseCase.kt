package cs.vsu.ru.domain.usecase.weather

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository

class GetWeatherDataUseCase(val repository: WeatherRepository) {

    suspend fun execute(location: Location): Weather {
        return repository.getWeather(location)
    }
}