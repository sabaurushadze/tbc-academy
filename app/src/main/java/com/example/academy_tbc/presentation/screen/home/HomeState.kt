package com.example.academy_tbc.presentation.screen.home

import com.example.academy_tbc.presentation.screen.home.model.post.UiPost
import com.example.academy_tbc.presentation.screen.home.model.story.UiStory

data class HomeState(
    val posts: List<UiPost> = listOf(),
    val stories: List<UiStory> = listOf(),
    val isLoading: Boolean = false,
)