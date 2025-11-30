package com.example.academy_tbc.data.paging.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.domain.common.AppError
import com.example.academy_tbc.domain.model.users.GetUsers
import com.example.academy_tbc.domain.repository.users.UsersRepository
import okio.IOException
import java.net.SocketTimeoutException

class UsersPagingSource(
    private val usersRepository: UsersRepository,
) : PagingSource<Int, GetUsers.GetUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetUsers.GetUser> {
        return try {
            val currentPage = params.key ?: 1
            val response = usersRepository.getUsers(currentPage)

            LoadResult.Page(
                data = response.data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage < response.totalPages) currentPage + 1 else null
            )
        } catch (e: Throwable) {
            val appError = when (e) {
                is SocketTimeoutException -> AppError.Network
                is IOException -> AppError.Network
                else -> AppError.Unknown
            }
            LoadResult.Error(PagingException(appError))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetUsers.GetUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}