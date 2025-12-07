package com.example.academy_tbc.domain.usecase.pc_parts

import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.repository.pc_parts.GetPcPartsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPcPartsUseCase @Inject constructor(
    private val getPcPartsRepository: GetPcPartsRepository,
) {
    operator fun invoke(): Flow<Resource<List<PcPart>>> {
        return getPcPartsRepository.getParts()
    }
}