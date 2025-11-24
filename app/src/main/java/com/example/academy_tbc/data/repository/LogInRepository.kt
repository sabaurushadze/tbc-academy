package com.example.academy_tbc.data.repository

import com.example.academy_tbc.common.ResponseHandler
import com.example.academy_tbc.data.login.LoginRequestDto
import com.example.academy_tbc.data.retrofit.LogInApiService
import jakarta.inject.Inject

class LogInRepository @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: LogInApiService
) {
    fun logIn(email: String, password: String) = responseHandler.safeApiCall {
        authService.login(LoginRequestDto(email = email, password = password))
    }
}