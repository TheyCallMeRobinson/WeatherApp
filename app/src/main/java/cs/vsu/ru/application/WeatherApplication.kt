package cs.vsu.ru.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import cs.vsu.ru.application.di.appModule
import cs.vsu.ru.application.di.dataModule
import cs.vsu.ru.application.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApplication : Application() {

    private val MAPKIT_API_KEY = "6e55d9ed-18b1-4138-a69a-9adf1f7347e0"

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WeatherApplication)
            fragmentFactory()
            modules(listOf(appModule, dataModule, domainModule))
        }

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

}
