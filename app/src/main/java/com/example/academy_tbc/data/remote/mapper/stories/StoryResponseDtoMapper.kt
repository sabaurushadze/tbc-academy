package com.example.academy_tbc.data.remote.mapper.stories

import com.example.academy_tbc.data.remote.dto.response.stories.StoryResponseDto
import com.example.academy_tbc.domain.model.story.Story

fun StoryResponseDto.toDomain(): Story {
    return Story(
        title = title,
        cover = cover
    )
}