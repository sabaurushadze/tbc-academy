package com.example.academy_tbc.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.data.remote.dto.response.users.UsersResponseDto
import com.example.academy_tbc.data.remote.service.users.UsersApiService

class UsersPagingSource(
    private val apiService: UsersApiService,
) : PagingSource<Int, UsersResponseDto.UserModelDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponseDto.UserModelDto> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getUsers(currentPage, params.loadSize)

            if (response.isSuccessful) {
                val body = response.body()
                    ?: return LoadResult.Error(Exception("HTTP error ${response.code()}"))

                LoadResult.Page(
                    data = body.data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < body.totalPages) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(Exception("HTTP error ${response.code()}"))

            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UsersResponseDto.UserModelDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}