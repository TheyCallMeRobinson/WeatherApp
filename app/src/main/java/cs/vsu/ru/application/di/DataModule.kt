package cs.vsu.ru.application.di

import androidx.room.Room
import cs.vsu.ru.database.AppDatabase
import cs.vsu.ru.database.dao.LocationDao
import cs.vsu.ru.database.dao.WeatherIconDao
import cs.vsu.ru.domain.repository.location.LocationRepository
import cs.vsu.ru.domain.repository.weather.WeatherRepository
import cs.vsu.ru.environment.WeatherServiceProvider
import cs.vsu.ru.mapper.WeatherMapper
import cs.vsu.ru.repository.LocationRepositoryImpl
import cs.vsu.ru.repository.WeatherRepositoryImpl
import cs.vsu.ru.service.WeatherIconService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            AppDatabase::class.java,
            "weather-app-database"
        ).fallbackToDestructiveMigration().build()
    }

    single<LocationDao> {
        get<AppDatabase>().locationDao()
    }

    single<WeatherIconDao> {
        get<AppDatabase>().weatherPictureDao()
    }

    single<LocationRepository> {
        LocationRepositoryImpl(
            locationDao = get(),
            context = androidContext()
        )
    }

    single<WeatherIconService> {
        WeatherIconService(context = get())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            retrofitServiceProvider = WeatherServiceProvider,
            weatherIconService = get(),
            weatherIconDao = get(),
            mapper = get(),
            context = get()
        )
    }

    single<WeatherMapper> {
        WeatherMapper()
    }
}