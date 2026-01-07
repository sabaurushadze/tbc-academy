package com.example.academy_tbc.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.academy_tbc.data.remote.mapper.users.UsersResponseDtoMapper
import com.example.academy_tbc.data.remote.paging.UsersPagingSource
import com.example.academy_tbc.data.remote.service.users.UsersApiService
import com.example.academy_tbc.domain.model.home.GetUsers
import com.example.academy_tbc.domain.repository.home.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: UsersApiService,
    private val usersResponseDtoMapper: UsersResponseDtoMapper
) : UsersRepository {
    override fun getUsers(config: PagingConfig): Flow<PagingData<GetUsers.GetUser>> {
        return Pager(
            config = config,
            pagingSourceFactory = { UsersPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map(usersResponseDtoMapper::mapToDomain)
        }
    }
}