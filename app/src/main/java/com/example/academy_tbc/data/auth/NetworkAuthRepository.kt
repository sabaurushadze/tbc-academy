package com.example.academy_tbc.data.auth

import com.example.academy_tbc.data.auth.home.UsersDto
import com.example.academy_tbc.data.auth.login.RequestLoginDto
import com.example.academy_tbc.data.auth.login.ResponseLoginDto
import com.example.academy_tbc.data.auth.register.RequestRegisterDto
import com.example.academy_tbc.data.auth.register.ResponseRegisterDto
import com.example.academy_tbc.data.network.AuthApiService
import com.example.academy_tbc.data.network.UsersApiService
import retrofit2.Response

interface AuthRepository {
    suspend fun register(user: RequestRegisterDto): Response<ResponseRegisterDto>
    suspend fun login(user: RequestLoginDto): Response<ResponseLoginDto>
}

interface UsersRepository {
    suspend fun getUsers(): Response<UsersDto>
}

class NetworkUsersRepository(
    private val usersApiService: UsersApiService
) : UsersRepository {
    override suspend fun getUsers(): Response<UsersDto> {
        return usersApiService.getUsers()
    }
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
}