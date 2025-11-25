package com.example.academy_tbc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.academy_tbc.data.remote.home.UsersResponseDto
import com.example.academy_tbc.data.remote.retrofit.UsersApiService
import com.example.academy_tbc.presentation.screen.home.UsersPagingSource
import jakarta.inject.Inject


class UsersRepository @Inject constructor(
    private val authService: UsersApiService,
) {
    fun getUsersPager(): Pager<Int, UsersResponseDto.User> =
        Pager(PagingConfig(pageSize = 6)) {
            UsersPagingSource(authService)
        }
}