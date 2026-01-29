package com.example.academy_tbc.data.repository.story

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.stories.toDomain
import com.example.academy_tbc.data.remote.service.story.StoryService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.story.Story
import com.example.academy_tbc.domain.repository.story.StoryRepository
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyService: StoryService,
    private val responseHandler: ApiResponseHandler,
) : StoryRepository {
    override suspend fun getStories(): Resource<List<Story>, DataError.Network> {
        return responseHandler.safeApiCall {
            storyService.getStories()
        }.mapList { it.toDomain() }
    }
}