package com.example.academy_tbc.domain.repository.pc_parts

import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow

interface SearchPcPartsRepository {
    fun search(query: String): Flow<Resource<List<PcPart>>>
}


