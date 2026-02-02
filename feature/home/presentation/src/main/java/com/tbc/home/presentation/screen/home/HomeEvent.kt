package com.tbc.home.presentation.screen.home

sealed class HomeEvent {
    data object GetForms : HomeEvent()
}