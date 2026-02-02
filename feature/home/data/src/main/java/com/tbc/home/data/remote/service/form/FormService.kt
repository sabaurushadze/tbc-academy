package com.tbc.home.data.remote.service.form

import com.tbc.home.data.remote.dto.response.form.FormResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface FormService {

    @GET(FORM)
    suspend fun getForms(): Response<List<List<FormResponseDto.FieldResponseDto>>>

    companion object {
        private const val FORM = "form"
    }

}