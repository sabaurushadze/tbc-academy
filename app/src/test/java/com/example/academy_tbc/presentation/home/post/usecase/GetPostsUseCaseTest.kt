package com.example.academy_tbc.presentation.home.post.usecase

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.errorOrNull
import com.example.academy_tbc.domain.common.getOrNull
import com.example.academy_tbc.domain.common.isFailure
import com.example.academy_tbc.domain.common.isSuccess
import com.example.academy_tbc.domain.model.post.Post
import com.example.academy_tbc.domain.repository.post.PostRepository
import com.example.academy_tbc.domain.usecase.post.GetPostsUseCase
import com.example.academy_tbc.presentation.base.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostsUseCaseTest : BaseUnitTest() {

    private val repository = mockk<PostRepository>()
    private val getPostsUseCase = GetPostsUseCase(repository)

    @Test
    fun `invoke returns posts successfully`() = runTest(testDispatcher) {
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
        coEvery { repository.getPosts() } returns Resource.Success(fakePosts)

        val result = getPostsUseCase()
        assertTrue(result.isSuccess())
        assertEquals(fakePosts, result.getOrNull())
    }

    @Test
    fun `invoke returns failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { repository.getPosts() } returns Resource.Failure(error)

        val result = getPostsUseCase()
        assertTrue(result.isFailure())
        assertEquals(error, result.errorOrNull())
    }
}