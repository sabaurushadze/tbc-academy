package com.example.academy_tbc.data.remote.home.repository

import com.example.academy_tbc.data.remote.home.service.UsersApiService
import jakarta.inject.Inject

class UsersRepository @Inject constructor(
    private val authService: UsersApiService,
) {
    suspend fun getUsers(page: Int) = authService.getUsers(page)
}