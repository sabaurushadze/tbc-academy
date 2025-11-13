package com.example.academy_tbc.data.network

import com.example.academy_tbc.data.auth.home.UsersDto
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.data.auth.login.ResponseLoginDto
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.data.auth.register.ResponseRegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun register(
        @Body user: RequestRegisterDto
    ): Response<ResponseRegisterDto>

    @POST("login")
    suspend fun login(
        @Body user: RequestLoginDto
    ): Response<ResponseLoginDto>

    @GET("users")
    suspend fun getUsers(
    ): Response<UsersDto>
}