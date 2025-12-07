package com.example.academy_tbc.domain.usecase.pc_parts

import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.repository.pc_parts.SearchPcPartsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPcPartsUseCase @Inject constructor(
    private val searchPcPartsRepository: SearchPcPartsRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<PcPart>>> {
        return searchPcPartsRepository.search(query)
    }

}