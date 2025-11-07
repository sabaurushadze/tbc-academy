package com.example.academy_tbc.box

import com.example.academy_tbc.field.Field
import kotlinx.serialization.Serializable

@Serializable
data class FieldBox(
    val fields: List<Field>
)