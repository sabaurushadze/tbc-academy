package com.example.academy_tbc.data.remote.dto.response.stories

import kotlinx.serialization.Serializable

@Serializable
data class StoryResponseDto(
    val title: String,
    val cover: String,
)
