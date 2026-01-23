package com.example.academy_tbc.presentation.screen.home.users.model

import com.example.academy_tbc.domain.model.user.MessageType

data class UiUser(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType,
)