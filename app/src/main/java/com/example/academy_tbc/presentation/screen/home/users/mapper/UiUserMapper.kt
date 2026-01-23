package com.example.academy_tbc.presentation.screen.home.users.mapper

import com.example.academy_tbc.domain.model.user.User
import com.example.academy_tbc.presentation.screen.home.users.model.UiUser

fun User.toPresentation(): UiUser {
    return UiUser(
        id = id,
        image = image,
        owner = owner,
        lastMessage = lastMessage,
        lastActive = lastActive,
        unreadMessages = unreadMessages,
        isTyping = isTyping,
        lastMessageType = lastMessageType,
    )
}

