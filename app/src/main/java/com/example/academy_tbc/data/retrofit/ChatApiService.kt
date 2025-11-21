package com.example.academy_tbc.data.retrofit

import com.example.academy_tbc.data.chat.ChatResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface ChatApiService {
    @GET("d7d9436b-21c5-43f7-82f9-2334163351cf")
    suspend fun getMessages(): Response<List<ChatResponseDto>>
}