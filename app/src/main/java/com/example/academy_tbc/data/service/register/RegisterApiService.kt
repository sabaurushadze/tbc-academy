package com.example.academy_tbc.data.service.register

import com.example.academy_tbc.data.model.request.register.RegisterRequestDto
import com.example.academy_tbc.data.model.response.register.RegisterResponseDto
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