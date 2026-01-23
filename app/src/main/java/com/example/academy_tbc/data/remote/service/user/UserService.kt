package com.example.academy_tbc.data.remote.service.user

import com.example.academy_tbc.data.remote.dto.response.users.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET(USERS)
    suspend fun getUsers(
        @Query("q") query: String? = null,
    ): Response<List<UserResponseDto>>


    companion object {
        private const val USERS = "users"
    }
}