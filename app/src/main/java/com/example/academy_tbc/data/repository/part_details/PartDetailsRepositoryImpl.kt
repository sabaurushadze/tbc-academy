package com.example.academy_tbc.data.repository.part_details

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.mapper.part_details.toDomain
import com.example.academy_tbc.data.service.pc_parts.PcPartsService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.part_details.PartDetail
import com.example.academy_tbc.domain.repository.part_details.PartDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartDetailsRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val api: PcPartsService,
) : PartDetailsRepository {
    override fun getPartDetails(id: Int): Flow<Resource<PartDetail, ApiError>> {
        return responseHandler.safeCall {
            api.getDetails(id)
        }.mapResource {
            it.toDomain()
        }
    }
}