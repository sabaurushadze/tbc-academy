package com.example.academy_tbc.domain.usecase.story

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.story.Story
import com.example.academy_tbc.domain.repository.story.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val storyRepository: StoryRepository,
) {
    suspend operator fun invoke(): Resource<List<Story>, DataError.Network> {
        return storyRepository.getStories()
    }
}