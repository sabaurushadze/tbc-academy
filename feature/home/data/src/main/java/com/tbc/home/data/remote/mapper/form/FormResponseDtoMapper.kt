package com.tbc.home.data.remote.mapper.form

import com.tbc.home.data.remote.dto.response.form.FormResponseDto
import com.tbc.home.domain.model.form.FieldType
import com.tbc.home.domain.model.form.Form
import com.tbc.home.domain.model.form.HintType

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