package com.example.academy_tbc.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.academy_tbc.data.mapper.pc_parts.toDomain
import com.example.academy_tbc.data.service.pc_parts.PcPartsService
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import javax.inject.Inject

class PcPartsPagingSource @Inject constructor(
    private val service: PcPartsService,
    private val query: PcPartsQuery,
) : PagingSource<Int, PcPart>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PcPart> {
        return try {
            val page = params.key ?: 1

            val response = service.search(
                query = query.titleLike,
                category = query.category,
                minPrice = query.minPrice,
                maxPrice = query.maxPrice,
                brand = query.brand,
                condition = query.condition,
                sortBy = query.sortBy,
                sortOrder = if (query.sortDescending) DESCENDING else ASCENDING,
                page = page,
                perPage = PER_PAGE_COUNT
            )

            if (response.isSuccessful) {
                val items = response.body() ?: emptyList()
                LoadResult.Page(
                    data = items.map { it.toDomain() },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PcPart>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    companion object {
        const val DESCENDING = "desc"
        const val ASCENDING = "asc"
        const val PER_PAGE_COUNT = 10
    }

}