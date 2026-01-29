package com.example.academy_tbc.domain.repository.story

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.story.Story

interface StoryRepository {
    suspend fun getStories(): Resource<List<Story>, DataError.Network>
}