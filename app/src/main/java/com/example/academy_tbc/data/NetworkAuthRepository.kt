package com.example.academy_tbc.data

import com.example.academy_tbc.screen.login.LoginDto
import com.example.academy_tbc.screen.login.LoginResponseDto
import com.example.academy_tbc.network.AuthApiService
import com.example.academy_tbc.screen.register.RegisterDto
import com.example.academy_tbc.screen.register.RegisterResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Repository idea avighe google codelab-idan
// https://developer.android.com/codelabs/basic-android-kotlin-compose-load-images#0
interface AuthRepository {

    suspend fun register(user: RegisterDto): RegisterResponseDto
    suspend fun login(user: LoginDto): LoginResponseDto
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService
) : AuthRepository {
    override suspend fun register(user: RegisterDto): RegisterResponseDto =
        withContext(Dispatchers.IO) {
            authApiService.register(user)
        }

    override suspend fun login(user: LoginDto): LoginResponseDto = withContext(Dispatchers.IO) {
        authApiService.login(user)
    }
}