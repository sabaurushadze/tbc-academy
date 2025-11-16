package com.example.academy_tbc.data.network

import com.example.academy_tbc.data.auth.home.ResponseUsersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1
    ): Response<ResponseUsersDto>
}