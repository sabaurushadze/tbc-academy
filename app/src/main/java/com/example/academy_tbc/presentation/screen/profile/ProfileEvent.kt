package com.example.academy_tbc.presentation.screen.profile


sealed class ProfileEvent {
    data object SignOut : ProfileEvent()
}