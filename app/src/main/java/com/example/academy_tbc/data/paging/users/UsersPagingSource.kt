package com.example.academy_tbc.data.paging.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.data.model.response.users.UsersResponseDto
import com.example.academy_tbc.data.service.users.UsersApiService
import com.example.academy_tbc.domain.common.AppError
import okio.IOException
import java.net.SocketTimeoutException

class UsersPagingSource(
    private val apiService: UsersApiService,
) : PagingSource<Int, UsersResponseDto.UserModelDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponseDto.UserModelDto> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getUsers(currentPage, params.loadSize)

            if (response.isSuccessful) {
                val body = response.body() ?: return LoadResult.Error(
                    PagingException(AppError.Server(response.code()))
                )
                LoadResult.Page(
                    data = body.data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < body.totalPages) currentPage + 1 else null
                )
            } else {
                val code = response.code()
                LoadResult.Error(PagingException(AppError.Server(code)))

            }
        } catch (e: Throwable) {
            val appError = when (e) {
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            LoadResult.Error(PagingException(appError))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersResponseDto.UserModelDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}