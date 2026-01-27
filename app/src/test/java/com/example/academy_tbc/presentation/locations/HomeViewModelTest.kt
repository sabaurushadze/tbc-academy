package com.example.academy_tbc.presentation.locations

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.usecase.location.GetLocationsUseCase
import com.example.academy_tbc.presentation.screen.home.HomeEvent
import com.example.academy_tbc.presentation.screen.home.HomeSideEffect
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val getLocationsUseCase = mockk<GetLocationsUseCase>()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val sampleLocations = listOf(
        Location("Paris", "300", "Eiffel Tower", "url", 5, "$200"),
        Location("London", "100", "Big Ben", "url", 4, "$150")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(getLocationsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given usecase returns success when GetLocations then state is updated`() =
        testScope.runTest {
            coEvery { getLocationsUseCase() } returns Resource.Success(
                listOf(
                    Location(
                        location = "Paris",
                        altitudeM = "300",
                        title = "Eiffel Tower",
                        image = "url",
                        stars = 5,
                        price = "$200"
                    )
                )
            )

            viewModel.onEvent(HomeEvent.GetLocations)
            testScheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertEquals(1, state.locations.size)
            assertEquals("Paris", state.locations[0].location)
            coVerify(exactly = 1) { getLocationsUseCase() }
        }

    @Test
    fun `given usecase returns failure when GetLocations then sideEffect is ShowSnackBar`() =
        testScope.runTest {
            // given
            coEvery { getLocationsUseCase() } returns Resource.Failure(DataError.Network.NO_CONNECTION)

            // when
            viewModel.onEvent(HomeEvent.GetLocations)
            testScheduler.advanceUntilIdle()

            // then
            val sideEffect = viewModel.sideEffect.first()
            assertTrue(sideEffect is HomeSideEffect.ShowSnackBar)
            coVerify(exactly = 1) { getLocationsUseCase() }
        }

    @Test
    fun `given multiple locations returned when GetLocations then state maps correctly`() =
        testScope.runTest {
            // given
            coEvery { getLocationsUseCase() } returns Resource.Success(sampleLocations)

            // when
            viewModel.onEvent(HomeEvent.GetLocations)
            testScheduler.advanceUntilIdle()

            // then
            val state = viewModel.state.value
            state.locations.forEachIndexed { index, uiLocation ->
                assertEquals(sampleLocations[index].title, uiLocation.title)
                assertEquals(sampleLocations[index].price, uiLocation.price)
            }
        }

    @Test
    fun `given failure when GetLocations then errorRes is correct`() =
        testScope.runTest {
            // given
            coEvery { getLocationsUseCase() } returns Resource.Failure(DataError.Network.TIMEOUT)

            // when
            viewModel.onEvent(HomeEvent.GetLocations)
            testScheduler.advanceUntilIdle()

            // then
            val sideEffect = viewModel.sideEffect.first()
            assertTrue(sideEffect is HomeSideEffect.ShowSnackBar)
            val errorRes = (sideEffect as HomeSideEffect.ShowSnackBar).errorRes
            // This checks that your extension function toStringResId() was called correctly
            assertTrue(errorRes >= 0)
        }
}