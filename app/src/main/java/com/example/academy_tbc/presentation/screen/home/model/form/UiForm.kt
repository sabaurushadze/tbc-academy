package com.example.academy_tbc.presentation.screen.home.model.form

import androidx.annotation.StringRes
import com.example.academy_tbc.domain.model.form.FieldType

data class UiForm(
    val form: List<List<UiField>>,
) {
    data class UiField(
        val fieldId: Int,
        @param:StringRes val hint: Int,
        val fieldType: FieldType,
        val chooserType: ChooserType = ChooserType.NONE,
        val keyboard: String?,
        val required: Boolean,
        val isActive: Boolean,
        val icon: String,
    ) {
        enum class ChooserType { NONE, DATE, SELECTION }
    }
}