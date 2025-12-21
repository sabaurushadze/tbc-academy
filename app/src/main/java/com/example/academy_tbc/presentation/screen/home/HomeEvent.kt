package com.example.academy_tbc.presentation.screen.home

sealed class HomeEvent {
    data class SendOtp(val phoneNumber: String) : HomeEvent()

}