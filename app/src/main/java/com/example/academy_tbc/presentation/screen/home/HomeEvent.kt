package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data object GetLocations : HomeEvent()
    data object GetPosts : HomeEvent()
}