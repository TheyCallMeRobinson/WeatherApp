package cs.vsu.ru.domain.usecase.weather

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.domain.repository.weather.WeatherRepository

class GetWeatherDataUseCase(val weatherRepository: WeatherRepository) {

    suspend fun execute(location: Location): Weather {
        return weatherRepository.getWeather(location)
    }
}