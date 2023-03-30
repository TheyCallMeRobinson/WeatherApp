package cs.vsu.ru.application.di

import cs.vsu.ru.domain.usecase.location.*
import cs.vsu.ru.domain.usecase.weather.GetWeatherDataUseCase
import cs.vsu.ru.domain.usecase.weather.GetWeatherIconUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetWeatherDataUseCase> {
        GetWeatherDataUseCase(weatherRepository = get())
    }

    factory<GetCurrentLocationUseCase> {
        GetCurrentLocationUseCase(locationRepository = get())
    }

    factory<GetSavedLocationsUseCase> {
        GetSavedLocationsUseCase(locationRepository = get())
    }

    factory<GetFavoriteLocationUseCase> {
        GetFavoriteLocationUseCase(locationRepository = get())
    }

    factory<SetCurrentLocationUseCase> {
        SetCurrentLocationUseCase(locationRepository = get())
    }

    factory<SetFavoriteLocationUseCase> {
        SetFavoriteLocationUseCase(locationRepository = get())
    }

    factory<RemoveSavedLocationUseCase> {
        RemoveSavedLocationUseCase(locationRepository = get())
    }

    factory<SaveLocationUseCase> {
        SaveLocationUseCase(locationRepository = get())
    }

    factory<GetWeatherIconUseCase> {
        GetWeatherIconUseCase(weatherRepository = get())
    }

    factory<FindLocationsByNameUseCase> {
        FindLocationsByNameUseCase(locationRepository = get())
    }

    factory<GetSavedLocationsExcludeUseCase> {
        GetSavedLocationsExcludeUseCase(locationRepository = get())
    }
}
