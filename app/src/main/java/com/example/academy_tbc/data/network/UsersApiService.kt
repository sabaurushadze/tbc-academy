package com.example.academy_tbc.data.network

import com.example.academy_tbc.data.auth.home.UsersDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(): Response<UsersDto>
}