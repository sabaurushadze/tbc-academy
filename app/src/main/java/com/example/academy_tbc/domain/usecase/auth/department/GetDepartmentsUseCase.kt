package com.example.academy_tbc.domain.usecase.auth.department

import com.example.academy_tbc.domain.common.ApiError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.auth.departments.Department
import com.example.academy_tbc.domain.repository.auth.departments.DepartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository,
) {
    operator fun invoke(): Flow<Resource<List<Department>, ApiError>> {
        return departmentRepository.getDepartments()
    }
}