package com.example.academy_tbc.data.remote.mapper.form

import com.example.academy_tbc.data.remote.dto.response.form.FormResponseDto
import com.example.academy_tbc.domain.model.form.FieldType
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.model.form.HintType

fun FormResponseDto.toDomain(): Form {
    return Form(
        form = form.map { innerList ->
            innerList.map { it.toDomain() }
        }
    )
}

fun FormResponseDto.FieldResponseDto.toDomain(): Form.Field {
    return Form.Field(
        fieldId = fieldId,
        hint = HintType.fromString(hint),
        fieldType = FieldType.fromString(fieldType),
        keyboard = keyboard,
        required = required,
        isActive = isActive,
        icon = icon
    )
}