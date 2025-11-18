package com.example.academy_tbc.presentation.screen.profile

sealed class ProfileEvent {
    data object RemoveUserToken : ProfileEvent()
    data object GetUserEmail : ProfileEvent()
}