package com.example.academy_tbc.domain.repository.users

import com.example.academy_tbc.domain.model.users.User
import com.example.academy_tbc.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun observeUsers(): Flow<List<User>>
    suspend fun refreshUsers(): Resource<Unit>
    suspend fun deleteUser(id: Int)
}