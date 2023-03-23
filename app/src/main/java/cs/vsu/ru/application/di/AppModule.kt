package cs.vsu.ru.application.di

import cs.vsu.ru.application.mapper.WeatherMapper
import cs.vsu.ru.application.viewmodel.AddNewLocationViewModel
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.application.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<WeatherMapper> {
        WeatherMapper()
    }

    viewModel<MainViewModel> {
        MainViewModel(
            getCurrentLocationUseCase = get(),
            getWeatherDataUseCase = get(),
            getWeatherIconUseCase = get(),
            setCurrentLocationUseCase = get(),
            weatherMapper = get(),
        )
    }

    viewModel<DrawerViewModel> {
        DrawerViewModel(
            getSavedLocationsUseCase = get(),
            getFavoriteLocationUseCase = get(),
            setFavoriteLocationUseCase = get(),
            removeSavedLocationUseCase = get()
        )
    }

    viewModel<AddNewLocationViewModel> {
        AddNewLocationViewModel(
            findLocationsByNameUseCase = get(),
            saveLocationUseCase = get()
        )
    }
}
