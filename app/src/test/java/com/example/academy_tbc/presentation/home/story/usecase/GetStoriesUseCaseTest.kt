package com.example.academy_tbc.presentation.home.story.usecase

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.errorOrNull
import com.example.academy_tbc.domain.common.getOrNull
import com.example.academy_tbc.domain.common.isFailure
import com.example.academy_tbc.domain.common.isSuccess
import com.example.academy_tbc.domain.model.story.Story
import com.example.academy_tbc.domain.repository.story.StoryRepository
import com.example.academy_tbc.domain.usecase.story.GetStoriesUseCase
import com.example.academy_tbc.presentation.base.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetStoriesUseCaseTest : BaseUnitTest() {

    private val repository = mockk<StoryRepository>()
    private val getStoriesUseCase = GetStoriesUseCase(repository)

    @Test
    fun `invoke returns stories successfully`() = runTest(testDispatcher) {
        val fakeStories = listOf(
            Story(
                title = "Test",
                cover = "https://i.ibb.co/gZkwfJym/beautiful-wide-shot-ocean-greenery-shoreline-with-amazing-cloudy-sky.jpg"
            )
        )
        coEvery { repository.getStories() } returns Resource.Success(fakeStories)

        val result = getStoriesUseCase()
        assertTrue(result.isSuccess())
        assertEquals(fakeStories, result.getOrNull())
    }

    @Test
    fun `invoke returns failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { repository.getStories() } returns Resource.Failure(error)

        val result = getStoriesUseCase()
        assertTrue(result.isFailure())
        assertEquals(error, result.errorOrNull())
    }
}