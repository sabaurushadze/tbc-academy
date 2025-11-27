package com.example.academy_tbc.data.remote.login.service

import com.example.academy_tbc.data.remote.login.model.LoginRequestDto
import com.example.academy_tbc.data.remote.login.model.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInApiService {
    @POST(LOGIN)
    suspend fun login(
        @Body user: LoginRequestDto,
    ): Response<LoginResponseDto>

    companion object {
        private const val LOGIN = "login"
    }
}