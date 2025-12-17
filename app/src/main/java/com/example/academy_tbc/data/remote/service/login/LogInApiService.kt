package com.example.academy_tbc.data.remote.service.login

import com.example.academy_tbc.data.remote.model.response.login.LogInResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface LogInApiService {
    @GET("")
    suspend fun login(): Response<LogInResponseDto>

    companion object {
        private const val LOGIN = "login"
    }
}