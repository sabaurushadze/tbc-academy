package com.example.academy_tbc.presentation.home.viewmodel

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.domain.model.story.Story
import com.example.academy_tbc.domain.usecase.post.GetPostsUseCase
import com.example.academy_tbc.domain.usecase.story.GetStoriesUseCase
import com.example.academy_tbc.presentation.base.BaseUnitTest
import com.example.academy_tbc.presentation.screen.home.HomeEvent
import com.example.academy_tbc.presentation.screen.home.HomeSideEffect
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import com.example.academy_tbc.presentation.screen.home.mapper.post.toPresentation
import com.example.academy_tbc.presentation.screen.home.mapper.story.toPresentation
import com.example.academy_tbc.presentation.util.toStringResId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseUnitTest() {

    private val getPostsUseCase = mockk<GetPostsUseCase>()
    private val getStoriesUseCase = mockk<GetStoriesUseCase>()
    private val viewModel = HomeViewModel(getStoriesUseCase, getPostsUseCase)

    @Test
    fun `getPosts updates state on success`() = runTest(testDispatcher) {
        val fakePosts = listOf(
            Post(
                avatar = "https://i.ibb.co/gZkwfJym/beautiful-wide-shot-ocean-greenery-shoreline-with-amazing-cloudy-sky.jpg",
                postDate = "12pm",
                fullName = "saba urushadze",
                images = listOf("https://i.ibb.co/gZkwfJym/beautiful-wide-shot-ocean-greenery-shoreline-with-amazing-cloudy-sky.jpg"),
                commentsCount = "4",
                likesCount = "4",
                postDesc = "blablabla",
                canComment = true,
                canPostPhoto = true
            )
        )
        coEvery { getPostsUseCase() } returns Resource.Success(fakePosts)

        viewModel.onEvent(HomeEvent.GetPosts)
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(fakePosts.map { it.toPresentation() }, state.posts)
    }

    @Test
    fun `getPosts emits snackbar on failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { getPostsUseCase() } returns Resource.Failure(error)

        viewModel.onEvent(HomeEvent.GetPosts)
        advanceUntilIdle()

        val sideEffect = viewModel.sideEffect.first()
        assertTrue(sideEffect is HomeSideEffect.ShowSnackBar)
        assertEquals(error.toStringResId(), (sideEffect as HomeSideEffect.ShowSnackBar).errorRes)
    }

    @Test
    fun `getStories updates state on success`() = runTest(testDispatcher) {
        val fakeStories = listOf(
            Story(
                title = "Test",
                cover = "https://i.ibb.co/gZkwfJym/beautiful-wide-shot-ocean-greenery-shoreline-with-amazing-cloudy-sky.jpg"
            )
        )
        coEvery { getStoriesUseCase() } returns Resource.Success(fakeStories)

        viewModel.onEvent(HomeEvent.GetStories)
        advanceUntilIdle()

        val state = viewModel.state.first()
        assertEquals(fakeStories.map { it.toPresentation() }, state.stories)
    }

    @Test
    fun `getStories emits snackbar on failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { getStoriesUseCase() } returns Resource.Failure(error)

        viewModel.onEvent(HomeEvent.GetStories)
        advanceUntilIdle()

        val sideEffect = viewModel.sideEffect.first()
        assertTrue(sideEffect is HomeSideEffect.ShowSnackBar)
        assertEquals(error.toStringResId(), (sideEffect as HomeSideEffect.ShowSnackBar).errorRes)
    }
}