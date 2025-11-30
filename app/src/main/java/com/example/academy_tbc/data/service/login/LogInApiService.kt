package com.example.academy_tbc.data.service.login

import com.example.academy_tbc.data.model.request.login.LogInRequestDto
import com.example.academy_tbc.data.model.response.login.LogInResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInApiService {
    @POST(LOGIN)
    suspend fun login(
        @Body user: LogInRequestDto,
    ): Response<LogInResponseDto>

    companion object {
        private const val LOGIN = "login"
    }
}