package com.example.academy_tbc.data.service.auth.departments

import com.example.academy_tbc.data.dto.response.auth.departments.DepartmentsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface DepartmentService {
    @GET("/api/Auth/departments")
    suspend fun getDepartments(): Response<List<DepartmentsResponseDto>>
}