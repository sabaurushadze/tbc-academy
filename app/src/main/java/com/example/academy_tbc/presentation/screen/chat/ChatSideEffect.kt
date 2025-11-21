package com.example.academy_tbc.presentation.screen.chat

sealed interface ChatSideEffect {
    data class ShowError(val message: String) : ChatSideEffect
}