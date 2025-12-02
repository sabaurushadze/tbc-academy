package com.example.academy_tbc.data.repository.users

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.academy_tbc.data.mapper.network.toDomain
import com.example.academy_tbc.data.paging.users.UsersPagingSource
import com.example.academy_tbc.data.service.users.UsersApiService
import com.example.academy_tbc.domain.model.users.GetUsers
import com.example.academy_tbc.domain.repository.users.UsersRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl @Inject constructor(
    private val apiService: UsersApiService,
) : UsersRepository {
    override fun getUsers(config: PagingConfig): Flow<PagingData<GetUsers.GetUser>> {
        return Pager(
            config = config,
            pagingSourceFactory = { UsersPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}