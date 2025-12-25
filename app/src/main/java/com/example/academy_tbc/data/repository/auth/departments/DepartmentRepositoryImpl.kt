package com.example.academy_tbc.data.repository.auth.departments

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.common.mapResource
import com.example.academy_tbc.data.mapper.auth.departments.toDomain
import com.example.academy_tbc.data.service.auth.departments.DepartmentService
import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.departments.Department
import com.example.academy_tbc.domain.repository.auth.departments.DepartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val api: DepartmentService,
    private val responseHandler: ApiResponseHandler,
) : DepartmentRepository {
    override fun getDepartments(): Flow<Resource<List<Department>, ApiError>> {
        return responseHandler.safeApiCall {
            api.getDepartments()
        }.mapResource {
            it.toDomain()
        }
    }
}