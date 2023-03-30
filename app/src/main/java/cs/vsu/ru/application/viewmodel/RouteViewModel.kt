package cs.vsu.ru.application.viewmodel

import androidx.lifecycle.ViewModel
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsExcludeUseCase
import cs.vsu.ru.domain.usecase.location.GetSavedLocationsUseCase

class RouteViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val getSavedLocationsExcludeUseCase: GetSavedLocationsExcludeUseCase
) : ViewModel() {

}