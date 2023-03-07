package cs.vsu.ru.application.di

import android.arch.persistence.room.Room
import cs.vsu.ru.application.WeatherApplication
import cs.vsu.ru.database.AppDatabase
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
            "database-name"
        ).build()
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            locationDao = get<AppDatabase>().locationDao(),
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