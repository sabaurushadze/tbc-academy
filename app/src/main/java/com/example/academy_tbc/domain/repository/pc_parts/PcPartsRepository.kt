package com.example.academy_tbc.domain.repository.pc_parts

import androidx.paging.PagingData
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import kotlinx.coroutines.flow.Flow

interface PcPartsRepository {
    fun getPcPartsPaging(
        query: PcPartsQuery,
        pageSize: Int,
    ): Flow<PagingData<PcPart>>
}