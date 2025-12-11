package com.example.academy_tbc.domain.usecase.pc_parts.paging

import androidx.paging.PagingData
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.model.pc_parts.PcPartsQuery
import com.example.academy_tbc.domain.repository.pc_parts.PcPartsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPcPartsPagingUseCase @Inject constructor(
    private val repository: PcPartsRepository
) {
    operator fun invoke(query: PcPartsQuery, pageSize: Int): Flow<PagingData<PcPart>> {
        return repository.getPcPartsPaging(
            pageSize = pageSize,
            query = query
        )
    }
}