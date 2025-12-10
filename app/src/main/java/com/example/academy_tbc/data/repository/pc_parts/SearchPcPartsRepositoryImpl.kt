package com.example.academy_tbc.data.repository.pc_parts

import com.example.academy_tbc.data.common.ResponseHandler
import com.example.academy_tbc.data.common.asResource
import com.example.academy_tbc.data.mapper.pcparts.toDomain
import com.example.academy_tbc.data.service.pc_parts.PcPartsService
import com.example.academy_tbc.domain.model.pc_parts.PcPart
import com.example.academy_tbc.domain.repository.pc_parts.SearchPcPartsRepository
import com.example.academy_tbc.domain.resource.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPcPartsRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val api: PcPartsService,
) : SearchPcPartsRepository {
    override fun search(query: String): Flow<Resource<List<PcPart>>> {
        return responseHandler.safeApiCall {
            api.search(query)
        }.asResource { list ->
            list.map { it.toDomain() }
        }
    }
}