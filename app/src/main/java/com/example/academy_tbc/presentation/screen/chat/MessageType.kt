package com.example.academy_tbc.presentation.screen.chat

enum class MessageType(val type: String) {
    TEXT("text"),
    VOICE("voice"),
    FILE("file");

    companion object {
        fun fromString(value: String): MessageType {
            return MessageType.entries.firstOrNull { it.type == value } ?: TEXT
        }
    }

}

