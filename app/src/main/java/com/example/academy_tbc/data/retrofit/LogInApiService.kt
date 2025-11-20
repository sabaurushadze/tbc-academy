package com.example.academy_tbc.data.retrofit

import com.example.academy_tbc.data.login.LoginRequestDto
import com.example.academy_tbc.data.login.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInApiService {
    @POST("login")
    suspend fun login(
        @Body user: LoginRequestDto
    ): Response<LoginResponseDto>
}