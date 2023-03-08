package cs.vsu.ru.application

import android.app.Application
import cs.vsu.ru.application.di.appModule
import cs.vsu.ru.application.di.dataModule
import cs.vsu.ru.application.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}
