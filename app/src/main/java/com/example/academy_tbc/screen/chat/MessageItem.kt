package com.example.academy_tbc.screen.chat

import java.util.UUID

data class MessageItem(
    val id: UUID,
    val message: String,
    val sentDate: Long,
    val messageType: MessageType
)
