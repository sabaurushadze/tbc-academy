package com.example.academy_tbc.data.remote.mapper.users

import com.example.academy_tbc.data.remote.dto.response.users.UserResponseDto
import com.example.academy_tbc.domain.model.user.MessageType
import com.example.academy_tbc.domain.model.user.User

fun UserResponseDto.toDomain(): User {
    return User(
        id = id,
        image = image,
        owner = owner,
        lastMessage = lastMessage,
        lastActive = lastActive,
        unreadMessages = unreadMessages,
        isTyping = isTyping,
        lastMessageType = MessageType.fromString(lastMessageType)
    )
}