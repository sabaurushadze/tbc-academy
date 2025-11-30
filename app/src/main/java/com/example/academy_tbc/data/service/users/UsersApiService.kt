package com.example.academy_tbc.data.service.users

import com.example.academy_tbc.data.model.response.users.UsersResponseDto
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