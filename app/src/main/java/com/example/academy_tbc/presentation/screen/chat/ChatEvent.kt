package com.example.academy_tbc.presentation.screen.chat

sealed class ChatEvent {
    data object GetUsers : ChatEvent()
    data class FilterChat(val query: String) : ChatEvent()
}