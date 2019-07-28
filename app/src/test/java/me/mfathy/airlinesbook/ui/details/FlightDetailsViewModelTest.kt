package me.mfathy.airlinesbook.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.airlinesbook.ImmediateSchedulerRuleUnitTests
import me.mfathy.airlinesbook.any
import me.mfathy.airlinesbook.argumentCaptor
import me.mfathy.airlinesbook.capture
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.schedules.GetScheduleFlightDetails
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import me.mfathy.airlinesbook.ui.state.ResourceState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for FlightDetailsViewModel
 */
@RunWith(JUnit4::class)
class FlightDetailsViewModelTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private var mockGetScheduleFlightDetails = mock(GetScheduleFlightDetails::class.java)

    private var flightDetailsViewModel = FlightDetailsViewModel(mockGetScheduleFlightDetails)

    @Captor
    val airportListCaptor = argumentCaptor<DisposableSubscriber<List<AirportEntity>>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFetchAirportsExecutesUseCase() {
        stubGetFlightDetailsUseCase()

        flightDetailsViewModel.fetchAirports(listOf(), "en", 1, 1)

        verify(mockGetScheduleFlightDetails, times(1)).execute(any(), any())
    }

    @Test
    fun testFetchAirportsReturnsSuccess() {

        stubGetFlightDetailsUseCase()

        val airports = listOf(AirportFactory.makeAirportEntity())

        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(any(), capture(airportListCaptor))
        airportListCaptor.value.onNext(airports)

        assertEquals(ResourceState.SUCCESS, flightDetailsViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsData() {

        stubGetFlightDetailsUseCase()

        val airports = listOf(AirportFactory.makeAirportEntity())

        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(any(), capture(airportListCaptor))

        airportListCaptor.value.onNext(airports)

        assertEquals(airports, flightDetailsViewModel.getAirportsLiveData().value?.data)
    }

    @Test
    fun testFetchAirportsReturnsError() {

        stubGetFlightDetailsUseCase()

        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(any(), capture(airportListCaptor))

        airportListCaptor.value.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, flightDetailsViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsMessageForError() {

        stubGetFlightDetailsUseCase()

        val errorMessage = DataFactory.randomString()
        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(any(), capture(airportListCaptor))

        airportListCaptor.value.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage, flightDetailsViewModel.getAirportsLiveData().value?.message)
    }

    private fun stubGetFlightDetailsUseCase() {
        `when`(mockGetScheduleFlightDetails.execute(any(), any())).thenReturn(flightDetailsViewModel.AirportsSubscriber())
    }
}
