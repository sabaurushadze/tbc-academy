package com.example.academy_tbc.domain.repository.auth.departments

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.departments.Department
import kotlinx.coroutines.flow.Flow

interface DepartmentRepository {
    fun getDepartments(): Flow<Resource<List<Department>, ApiError>>
}
