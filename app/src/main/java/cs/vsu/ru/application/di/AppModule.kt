package cs.vsu.ru.application.di

import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.database.AppDatabase
import cs.vsu.ru.database.dao.LocationDao
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel> {
        MainViewModel(
            getCurrentLocationUseCase = get(),
            getWeatherDataUseCase = get()
        )
    }
}
