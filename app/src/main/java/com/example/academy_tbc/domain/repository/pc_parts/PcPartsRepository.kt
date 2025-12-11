package com.example.academy_tbc.domain.repository.pc_parts

import androidx.paging.PagingData
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PcPartsRepository {
    fun getPcPartsPaging(
        query: PcPartsQuery,
        pageSize: Int,
    ): Flow<PagingData<PcPart>>
}



    //    fun getParts(): Flow<Resource<List<PcPart>>>
//    fun search(query: String): Flow<Resource<List<PcPart>>>
//    fun getPartsByCategory(query: String): Flow<Resource<List<PcPart>>>
