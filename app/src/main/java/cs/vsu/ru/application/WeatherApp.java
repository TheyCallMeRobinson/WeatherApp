package cs.vsu.ru.application;

import android.app.Application;

public final class WeatherApp extends Application {
    private static WeatherApp instance;

    public WeatherApp getInstance() {
        if (instance == null)
            return new WeatherApp();
        else
            return instance;
    }
}
