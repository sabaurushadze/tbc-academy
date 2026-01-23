package com.example.academy_tbc.domain.model.user

data class User(
    val id: Int,
    val image: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType
)

enum class MessageType {
    TEXT,
    VOICE,
    FILE;

    companion object {
        fun fromString(value: String): MessageType {
            return when (value) {
                "text" -> TEXT
                "voice" -> VOICE
                "file" -> FILE
                else -> TEXT
            }
        }
    }
}