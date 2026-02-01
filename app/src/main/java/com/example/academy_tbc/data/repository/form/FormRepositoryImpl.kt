package com.example.academy_tbc.data.repository.form

import com.example.academy_tbc.data.common.ApiResponseHandler
import com.example.academy_tbc.data.remote.dto.response.form.FormResponseDto
import com.example.academy_tbc.data.remote.mapper.form.toDomain
import com.example.academy_tbc.data.remote.service.form.FormService
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.map
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.repository.form.FormRepository
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val formService: FormService,
    private val responseHandler: ApiResponseHandler,
) : FormRepository {
    override suspend fun getForms(): Resource<Form, DataError.Network> {
        return responseHandler.safeApiCall {
            formService.getForms()
        }.map {
            FormResponseDto(form = it).toDomain()
        }
    }
}