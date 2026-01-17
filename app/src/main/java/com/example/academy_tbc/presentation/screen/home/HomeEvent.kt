package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data object GetCategories : HomeEvent()
    data object GetOutfits : HomeEvent()
    data class CategoryClicked(val id: Int) : HomeEvent()
    data class FavoriteClicked(val id: Int) : HomeEvent()
}