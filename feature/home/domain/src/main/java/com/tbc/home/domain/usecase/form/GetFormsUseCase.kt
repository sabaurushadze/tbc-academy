package com.tbc.home.domain.usecase.form

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.map
import com.tbc.home.domain.model.form.Form
import com.tbc.home.domain.repository.form.FormRepository
import javax.inject.Inject

class GetFormsUseCase @Inject constructor(
    private val formRepository: FormRepository,
) {
    suspend operator fun invoke(): Resource<List<List<Form.Field>>, DataError.Network> {
        return formRepository.getForms().map { form ->
            form.form
        }
    }
}