package com.tbc.home.data.repository.form

import com.tbc.core.data.remote.network.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.map
import com.tbc.home.data.remote.dto.response.form.FormResponseDto
import com.tbc.home.data.remote.mapper.form.toDomain
import com.tbc.home.data.remote.service.form.FormService
import com.tbc.home.domain.model.form.Form
import com.tbc.home.domain.repository.form.FormRepository
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