package com.example.academy_tbc.data.remote.service.login

import com.example.academy_tbc.data.remote.dto.request.login.LoginRequestDto
import com.example.academy_tbc.data.remote.dto.response.login.LogInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LogInApiService {
    @POST(LOGIN)
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LogInResponseDto>

    companion object {
        private const val LOGIN = "login"
    }
}