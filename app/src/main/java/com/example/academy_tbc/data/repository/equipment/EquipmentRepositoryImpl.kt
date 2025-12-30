package com.example.academy_tbc.data.repository.equipment

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.remote.mapper.toDomain
import com.example.academy_tbc.data.remote.service.login.EquipmentApiService
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.equipment.Equipment
import com.example.academy_tbc.domain.repository.equipment.EquipmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val responseHandler: ApiResponseHandler,
    private val authService: EquipmentApiService,
) : EquipmentRepository {
    override fun getEquipment(): Flow<Resource<List<Equipment>>> {
        return responseHandler.safeApiCall {
            authService.login()
        }.mapResource {
            it.toDomain()
        }
    }
}
