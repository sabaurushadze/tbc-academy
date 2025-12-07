package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data object GetParts : HomeEvent()
    data class Search(val query: String) : HomeEvent()
}