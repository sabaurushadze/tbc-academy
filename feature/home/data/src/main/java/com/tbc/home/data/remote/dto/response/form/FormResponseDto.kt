package com.tbc.home.data.remote.dto.response.form

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FormResponseDto(
    val form: List<List<FieldResponseDto>>
) {
    @Serializable
    data class FieldResponseDto(
        @SerialName("field_id") val fieldId: Int,
        val hint: String,
        @SerialName("field_type") val fieldType: String,
        val keyboard: String? = null,
        val required: Boolean,
        @SerialName("is_active") val isActive: Boolean,
        val icon: String
    )
}