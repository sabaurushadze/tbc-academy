package com.example.academy_tbc.presentation.screen.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.common.AppException
import com.example.academy_tbc.data.remote.home.UsersResponseDto
import com.example.academy_tbc.data.remote.retrofit.UsersApiService
import okio.IOException
import java.net.SocketTimeoutException

class UsersPagingSource(
    private val usersApiService: UsersApiService,
) : PagingSource<Int, UsersResponseDto.User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponseDto.User> {
        return try {
            val currentPage = params.key ?: 1
            val response = usersApiService.getUsers(currentPage)

            if (response.isSuccessful) {
                val users = response.body()?.data ?: emptyList()
                val totalPages = response.body()?.totalPages ?: 1

                LoadResult.Page(
                    data = users,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < totalPages) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(AppException.Http(code = response.code()))
            }
        } catch (e: Throwable) {
            val exception = when (e) {
                is SocketTimeoutException -> AppException.Timeout(e)
                is IOException -> AppException.Network(e)
                else -> AppException.Unknown(e)
            }
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersResponseDto.User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}