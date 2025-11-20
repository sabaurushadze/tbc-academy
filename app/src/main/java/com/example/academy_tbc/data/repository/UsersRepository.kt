package com.example.academy_tbc.data.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.remote.retrofit.UsersApiService
import jakarta.inject.Inject


class UsersRepository @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val authService: UsersApiService
) {
    fun getUsers() = responseHandler.safeApiCall {
        authService.getUsers()
    }
}