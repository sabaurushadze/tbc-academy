package com.example.academy_tbc.data.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.remote.register.RegisterRequestDto
import com.example.academy_tbc.data.remote.retrofit.RetrofitClient

object RegisterRepository {
    fun register(email: String, password: String) = ResponseHandler.safeApiCall {
        RetrofitClient.registerApiService.register(RegisterRequestDto(email = email, password = password))
    }
}