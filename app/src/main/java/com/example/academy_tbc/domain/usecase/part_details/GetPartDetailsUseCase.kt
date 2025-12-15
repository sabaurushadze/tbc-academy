package com.example.academy_tbc.domain.usecase.part_details

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.part_details.PartDetail
import com.example.academy_tbc.domain.repository.part_details.PartDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPartDetailsUseCase @Inject constructor(
    private val partDetailsRepository: PartDetailsRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<PartDetail, ApiError>> {
        return partDetailsRepository.getPartDetails(id)
    }
}