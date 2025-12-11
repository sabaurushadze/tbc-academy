package com.example.academy_tbc.domain.usecase.pc_parts

import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.repository.pc_parts.PcPartsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class GetPartsByCategoryUseCase @Inject constructor(
//    private val searchPcPartsRepository: PcPartsRepository,
//) {
//    operator fun invoke(query: String): Flow<Resource<List<PcPart>>> {
//        return searchPcPartsRepository.getPartsByCategory(query)
//    }
//}