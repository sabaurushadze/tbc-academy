package com.example.academy_tbc.data.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.remote.login.LoginRequestDto
import com.example.academy_tbc.data.remote.retrofit.RetrofitClient

object LogInRepository {
    fun logIn(email: String, password: String) = ResponseHandler.safeApiCall {
        RetrofitClient.logInApiService.login(LoginRequestDto(email = email, password = password))
    }
}