package com.example.academy_tbc.data.remote.register.service

import com.example.academy_tbc.data.remote.register.model.RegisterRequestDto
import com.example.academy_tbc.data.remote.register.model.RegisterResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST(REGISTER)
    suspend fun register(
        @Body user: RegisterRequestDto
    ): Response<RegisterResponseDto>

    companion object {
        private const val REGISTER = "register"
    }
}