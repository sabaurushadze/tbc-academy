package com.example.academy_tbc.data.remote.retrofit

import com.example.academy_tbc.data.remote.register.RegisterRequestDto
import com.example.academy_tbc.data.remote.register.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("register")
    suspend fun register(
        @Body user: RegisterRequestDto
    ): Response<RegisterResponseDto>
}