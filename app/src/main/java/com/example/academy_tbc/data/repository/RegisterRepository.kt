package com.example.academy_tbc.data.repository

import com.example.academy_tbc.common.ResponseHandler
import com.example.academy_tbc.data.remote.register.RegisterRequestDto
import com.example.academy_tbc.data.remote.retrofit.RegisterApiService
import jakarta.inject.Inject


class RegisterRepository @Inject constructor (
    private val responseHandler: ResponseHandler,
    private val authService: RegisterApiService
) {
    fun register(email: String, password: String) = responseHandler.safeApiCall {
        authService.register(RegisterRequestDto(email = email, password = password))
    }
}