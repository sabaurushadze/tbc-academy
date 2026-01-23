package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data class GetUsers(val query: String? = null) : HomeEvent()
    data class QueryChanged(val query: String? = null) : HomeEvent()
}