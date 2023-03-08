package cs.vsu.ru.application.di

import androidx.room.Room
import cs.vsu.ru.application.WeatherApplication
import cs.vsu.ru.database.AppDatabase
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.domain.repository.location.LocationRepository
import cs.vsu.ru.domain.repository.weather.WeatherRepository
import cs.vsu.ru.environment.WeatherServiceProvider
import cs.vsu.ru.mapper.WeatherMapper
import cs.vsu.ru.repository.LocationRepositoryImpl
import cs.vsu.ru.repository.WeatherRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java,
            "weather-app-database"
        ).build()
    }

    single<LocationDao> {
        get<AppDatabase>().locationDao()
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            locationDao = get(),
            context = androidContext()
        )
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            serviceProvider = WeatherServiceProvider,
            mapper = get()
        )
    }

    single<WeatherMapper> {
        WeatherMapper()
    }
}