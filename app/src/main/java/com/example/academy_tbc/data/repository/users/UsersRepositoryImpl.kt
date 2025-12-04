package com.example.academy_tbc.data.repository.users

import com.example.academy_tbc.data.local.mapper.toDomain
import com.example.academy_tbc.data.local.room.users.UserDao
import com.example.academy_tbc.data.remote.mapper.toEntity
import com.example.academy_tbc.data.remote.service.users.UsersApiService
import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.users.User
import com.example.academy_tbc.domain.repository.users.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApiService,
    private val dao: UserDao,
) : UsersRepository {
    override suspend fun deleteUser(id: Int) {
        dao.deleteUserById(id)
    }

    override fun observeUsers(): Flow<List<User>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun refreshUsers(): Resource<Unit> {
        return try {
            val response = api.getUsers()

            if (response.isSuccessful) {
                val body = response.body() ?: return Resource.Error(AppError.Unknown)

                dao.insertUsers(*body.map { it.toEntity() }.toTypedArray())
                Resource.Success(Unit)
            } else {
                Resource.Error(AppError.Server(response.code()))
            }
        } catch (e: Exception) {
            val appError = when (e) {
                is HttpException -> AppError.Server(e.code())
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            Resource.Error(appError)
        }
    }
}