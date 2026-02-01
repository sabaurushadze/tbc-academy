package com.example.academy_tbc.domain.repository.form

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.form.Form

interface FormRepository {
    suspend fun getForms(): Resource<Form, DataError.Network>
}