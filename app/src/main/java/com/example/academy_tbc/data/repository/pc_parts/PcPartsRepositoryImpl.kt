package com.example.academy_tbc.data.repository.pc_parts

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.academy_tbc.data.paging.PcPartsPagingSource
import com.example.academy_tbc.data.service.pc_parts.PcPartsService
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.domain.repository.pc_parts.PcPartsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PcPartsRepositoryImpl @Inject constructor(
    private val service: PcPartsService,
) : PcPartsRepository {
    override fun getPcPartsPaging(
        query: PcPartsQuery,
        pageSize: Int,
    ): Flow<PagingData<PcPart>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PcPartsPagingSource(query = query, service = service) }
        ).flow
    }
}
