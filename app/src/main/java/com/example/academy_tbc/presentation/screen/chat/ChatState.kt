package com.example.academy_tbc.presentation.screen.chat

data class ChatState(
    val messages: List<MessageItem> = emptyList(),
    val isLoading: Boolean = false,
)