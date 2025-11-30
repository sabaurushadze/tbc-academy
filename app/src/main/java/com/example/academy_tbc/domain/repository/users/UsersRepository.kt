package com.example.academy_tbc.domain.repository.users

import com.example.academy_tbc.domain.model.users.GetUsers

interface UsersRepository {
    suspend fun getUsers(page: Int): GetUsers
}