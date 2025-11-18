package com.example.academy_tbc.data.repository

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.remote.retrofit.RetrofitClient

object UsersRepository {
    fun getUsers() = ResponseHandler.safeApiCall {
        RetrofitClient.usersApiService.getUsers()
    }
}