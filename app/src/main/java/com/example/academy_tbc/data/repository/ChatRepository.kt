package com.example.academy_tbc.data.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.retrofit.ChatApiService
import jakarta.inject.Inject

class ChatRepository @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: ChatApiService,
) {
    fun getChatMessages() = responseHandler.safeApiCall {
        authService.getMessages()
    }
}