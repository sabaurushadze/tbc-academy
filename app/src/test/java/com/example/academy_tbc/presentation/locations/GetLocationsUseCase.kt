package com.example.academy_tbc.presentation.locations

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.location.Location
import com.example.academy_tbc.domain.repository.location.LocationRepository
import com.example.academy_tbc.domain.usecase.location.GetLocationsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocationsUseCaseTest {

    private lateinit var useCase: GetLocationsUseCase
    private val repository = mockk<LocationRepository>()

    private val sampleLocations = listOf(
        Location("Paris", "300", "Eiffel Tower", "url", 5, "$200")
    )

    @Before
    fun setup() {
        useCase = GetLocationsUseCase(repository)
    }

    @Test
    fun `given repository returns success when invoke then returns success`() = runTest {
        // given
        coEvery { repository.getLocations() } returns Resource.Success(sampleLocations)

        // when
        val result = useCase()

        // then
        assertTrue(result is Resource.Success)
        coVerify(exactly = 1) { repository.getLocations() }
    }

    @Test
    fun `given repository returns network failure when invoke then returns failure`() = runTest {
        // given
        coEvery { repository.getLocations() } returns Resource.Failure(DataError.Network.NO_CONNECTION)

        // when
        val result = useCase()

        // then
        assertTrue(result is Resource.Failure)
        coVerify(exactly = 1) { repository.getLocations() }
    }
}