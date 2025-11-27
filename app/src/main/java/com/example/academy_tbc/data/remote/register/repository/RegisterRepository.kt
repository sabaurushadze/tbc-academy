package com.example.academy_tbc.data.remote.register.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.remote.register.model.RegisterRequestDto
import com.example.academy_tbc.data.remote.register.service.RegisterApiService
import jakarta.inject.Inject

class RegisterRepository @Inject constructor (
    private val responseHandler: ResponseHandler,
    private val authService: RegisterApiService
) {
    fun register(email: String, password: String) = responseHandler.safeApiCall {
        authService.register(RegisterRequestDto(email = email, password = password))
    }
}