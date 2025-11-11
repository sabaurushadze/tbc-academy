package com.example.academy_tbc.network

import com.example.academy_tbc.screen.login.LoginDto
import com.example.academy_tbc.screen.login.LoginResponseDto
import com.example.academy_tbc.screen.register.RegisterDto
import com.example.academy_tbc.screen.register.RegisterResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers(
        "x-api-key: reqres-free-v1"
    )
    @POST("api/register")
    suspend fun register(
        @Body user: RegisterDto
    ): RegisterResponseDto

    @Headers(
        "x-api-key: reqres-free-v1"
    )
    @POST("api/login")
    suspend fun login(
        @Body user: LoginDto
    ): LoginResponseDto
}