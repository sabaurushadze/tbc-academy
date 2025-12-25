package com.example.academy_tbc.data.service.auth.sign_in

import com.example.academy_tbc.data.dto.request.auth.sign_in.SignInRequestDto
import com.example.academy_tbc.data.dto.response.auth.sign_in.SignInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/api/Auth/login")
    suspend fun signIn(
        @Body body: SignInRequestDto,
    ): Response<SignInResponseDto>
}