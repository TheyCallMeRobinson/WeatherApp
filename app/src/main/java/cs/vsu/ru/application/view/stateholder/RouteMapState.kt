package cs.vsu.ru.application.view.stateholder

enum class RouteMapState {
    OBSERVE_MAP,
    OBSERVE_MAP_FOR_START_LOCATION,
    OBSERVE_MAP_FOR_END_LOCATION,
    OBSERVE_MAP_FOR_ROUTE
}

enum class RouteMenuState {
    IDLE,
    CHOOSING_START_LOCATION,
    CHOOSING_END_LOCATION,
    SHOWING_ROUTE_WEATHER_DATA
}

enum class RouteViewState {
    OPEN_MAP,
    OPEN_MENU
}