package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data object GetPosts : HomeEvent()
    data object GetStories : HomeEvent()
}