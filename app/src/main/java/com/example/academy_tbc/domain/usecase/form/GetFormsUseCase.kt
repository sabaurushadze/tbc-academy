package com.example.academy_tbc.domain.usecase.form

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.map
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.repository.form.FormRepository
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