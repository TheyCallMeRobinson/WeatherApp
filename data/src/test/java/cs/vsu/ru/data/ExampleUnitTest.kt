package cs.vsu.ru.data

import cs.vsu.ru.domain.model.location.Location
import cs.vsu.ru.domain.repository.location.LocationRepository
import cs.vsu.ru.domain.usecase.location.RemoveSavedLocationUseCase
import cs.vsu.ru.domain.usecase.location.SaveLocationUseCase
import cs.vsu.ru.repository.LocationRepositoryImpl
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val mockLocationRepositoryImpl: LocationRepository = Mockito.spy(LocationRepositoryImpl::class.java)
    private val removeSavedLocationUseCase: RemoveSavedLocationUseCase = RemoveSavedLocationUseCase(mockLocationRepositoryImpl)

    @Test(expected = Exception::class)
    fun addition_savedLocation1() {
        Mockito.`when`(mockLocationRepositoryImpl.getSavedLocations()).thenReturn(listOf(Location()))
        removeSavedLocationUseCase.execute("Moscow")
    }
}