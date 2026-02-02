package com.tbc.home.domain.repository.form

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.home.domain.model.form.Form

interface FormRepository {
    suspend fun getForms(): Resource<Form, DataError.Network>
}