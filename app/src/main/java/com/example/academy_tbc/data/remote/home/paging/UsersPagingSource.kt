package com.example.academy_tbc.data.remote.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.R
import com.example.academy_tbc.data.common.AppException
import com.example.academy_tbc.data.remote.home.model.UsersResponseDto
import com.example.academy_tbc.data.remote.home.repository.UsersRepository
import okio.IOException
import java.net.SocketTimeoutException

class UsersPagingSource(
    private val usersRepository: UsersRepository,
) : PagingSource<Int, UsersResponseDto.User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponseDto.User> {
        return try {
            val currentPage = params.key ?: 1
            val response = usersRepository.getUsers(currentPage)

            if (response.isSuccessful) {
                val users = response.body()?.data ?: emptyList()
                val totalPages = response.body()?.totalPages ?: 1

                LoadResult.Page(
                    data = users,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < totalPages) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(AppException.ServerError(errorCode = response.code()))
            }
        } catch (e: Throwable) {
            val errorRes = when (e) {
                is SocketTimeoutException -> AppException.ErrorRes(R.string.timeout)
                is IOException -> AppException.ErrorRes(R.string.no_internet)
                else -> AppException.ErrorRes(R.string.something_went_wrong_please_try_again)
            }
            LoadResult.Error(errorRes)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersResponseDto.User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}