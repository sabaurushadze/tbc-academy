package com.example.academy_tbc.data.service.auth.user_info

import com.example.academy_tbc.data.dto.response.auth.user_info.UserInfoResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("/api/v1/userInfo")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserInfoResponseDto>
}