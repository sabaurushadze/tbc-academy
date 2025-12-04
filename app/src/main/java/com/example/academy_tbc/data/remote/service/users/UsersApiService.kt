package com.example.academy_tbc.data.remote.service.users

import com.example.academy_tbc.data.remote.model.response.users.UsersResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {
    @GET(USERS)
    suspend fun getUsers(): Response<List<UsersResponseDto>>

    companion object {
        private const val USERS = "3668d139-e182-4fe2-b909-6259524117cb"
    }
}