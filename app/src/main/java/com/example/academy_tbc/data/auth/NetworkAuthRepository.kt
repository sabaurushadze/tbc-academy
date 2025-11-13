package com.example.academy_tbc.data.auth

import com.example.academy_tbc.data.auth.home.UsersDto
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.data.auth.login.ResponseLoginDto
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.data.auth.register.ResponseRegisterDto
import com.example.academy_tbc.data.network.AuthApiService
import retrofit2.Response

interface AuthRepository {

    suspend fun register(user: RequestRegisterDto): Response<ResponseRegisterDto>
    suspend fun login(user: RequestLoginDto): Response<ResponseLoginDto>
    suspend fun getUsers(): Response<UsersDto>
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(user: RequestRegisterDto): Response<ResponseRegisterDto> {
        return authApiService.register(user)
    }

    override suspend fun login(user: RequestLoginDto): Response<ResponseLoginDto> {
        return authApiService.login(user)
    }

    override suspend fun getUsers(): Response<UsersDto> {
        return authApiService.getUsers()
    }
}