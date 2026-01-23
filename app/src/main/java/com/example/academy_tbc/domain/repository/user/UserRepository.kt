package com.example.academy_tbc.domain.repository.user

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.user.User

interface UserRepository {
    suspend fun getUsers(query: String? = null): Resource<List<User>, DataError.Network>
}