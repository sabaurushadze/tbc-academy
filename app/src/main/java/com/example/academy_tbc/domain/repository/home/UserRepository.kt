package com.example.academy_tbc.domain.repository.home

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.academy_tbc.domain.model.home.GetUsers
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUsers(config: PagingConfig): Flow<PagingData<GetUsers.GetUser>>
}