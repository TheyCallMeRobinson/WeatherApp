package cs.vsu.ru.domain.usecase.weather

import cs.vsu.ru.domain.repository.weather.WeatherRepository

class GetWeatherIconUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun execute(iconName: String): ByteArray {
        val icon = weatherRepository.getWeatherIconFromPicasso(iconName)

        return icon
    }
}