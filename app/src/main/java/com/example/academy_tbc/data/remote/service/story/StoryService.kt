package com.example.academy_tbc.data.remote.service.story

import com.example.academy_tbc.data.remote.dto.response.stories.StoryResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface StoryService {

    @GET(STORIES)
    suspend fun getStories(): Response<List<StoryResponseDto>>

    companion object {
        private const val STORIES = "stories"
    }

}