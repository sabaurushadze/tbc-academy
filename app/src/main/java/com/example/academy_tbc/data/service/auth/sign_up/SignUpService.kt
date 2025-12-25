package com.example.academy_tbc.data.service.auth.sign_up

import com.example.academy_tbc.data.dto.request.auth.sign_up.SignUpRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/Auth/register")
    suspend fun signUp(
        @Body body: SignUpRequestDto,
    ): Response<Unit>
}