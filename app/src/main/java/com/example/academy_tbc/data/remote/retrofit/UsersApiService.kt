package com.example.academy_tbc.data.remote.retrofit

import com.example.academy_tbc.data.remote.home.UsersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1
    ): Response<UsersResponseDto>
}