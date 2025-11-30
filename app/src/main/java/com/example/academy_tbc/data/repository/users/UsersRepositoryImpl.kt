package com.example.academy_tbc.data.repository.users

import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.data.mapper.network.toDomain
import com.example.academy_tbc.data.paging.users.PagingException
import com.example.academy_tbc.data.service.users.UsersApiService
import com.example.academy_tbc.domain.model.users.GetUsers
import com.example.academy_tbc.domain.repository.users.UsersRepository
import jakarta.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val authService: UsersApiService,
) : UsersRepository {
    override suspend fun getUsers(page: Int): GetUsers {
        val response = authService.getUsers(page)
        return if (response.isSuccessful) {
            response.body()?.toDomain() ?: throw PagingException(
                appError = AppError.Server(response.code())
            )
        } else {
            throw PagingException(
                appError = AppError.Server(response.code())
            )
        }
    }
}