package com.example.academy_tbc.data.remote.dto.response.users

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: String
)