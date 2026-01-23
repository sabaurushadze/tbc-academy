package com.example.academy_tbc.data.repository.user

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.mapper.users.toDomain
import com.example.academy_tbc.data.remote.service.user.UserService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.mapList
import com.example.academy_tbc.domain.model.user.User
import com.example.academy_tbc.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val userService: UserService,
) : UserRepository {
    override suspend fun getUsers(query: String?): Resource<List<User>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            userService.getUsers(query)
        }.mapList { it.toDomain() }
    }
}