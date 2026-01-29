package com.example.academy_tbc.presentation.screen.home.mapper.story

import com.example.academy_tbc.domain.model.story.Story
import com.example.academy_tbc.presentation.screen.home.model.story.UiStory

fun Story.toPresentation(): UiStory {
    return UiStory(
        title = title,
        cover = cover
    )
}