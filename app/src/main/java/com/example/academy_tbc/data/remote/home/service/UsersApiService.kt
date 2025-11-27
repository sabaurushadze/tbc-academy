package com.example.academy_tbc.data.remote.home.service

import com.example.academy_tbc.data.remote.home.model.UsersResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApiService {
    @GET(USERS)
    suspend fun getUsers(
        @Query("page") page: Int
    ): Response<UsersResponseDto>

    companion object {
        private const val USERS = "users"
    }
}