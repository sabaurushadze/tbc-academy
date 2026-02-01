package com.example.academy_tbc.presentation.screen.home.mapper.form

import androidx.annotation.StringRes
import com.example.academy_tbc.R
import com.example.academy_tbc.domain.model.form.FieldType
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.model.form.HintType
import com.example.academy_tbc.presentation.screen.home.model.form.UiForm

fun Form.Field.toPresentation(): UiForm.UiField {
    val chooserType = when (fieldType) {
        FieldType.CHOOSER -> when (hint) {
            HintType.BIRTHDAY -> UiForm.UiField.ChooserType.DATE
            HintType.GENDER -> UiForm.UiField.ChooserType.SELECTION
            else -> UiForm.UiField.ChooserType.NONE
        }
        else -> UiForm.UiField.ChooserType.NONE
    }

    return UiForm.UiField(
        fieldId = fieldId,
        hint = hint.toStringRes(),
        chooserType = chooserType,
        fieldType = fieldType,
        keyboard = keyboard,
        required = required,
        isActive = isActive,
        icon = icon
    )
}

@StringRes
fun HintType.toStringRes(): Int {
    return when (this) {
        HintType.USERNAME -> R.string.username
        HintType.EMAIL -> R.string.email
        HintType.PHONE -> R.string.phone
        HintType.FULLNAME -> R.string.full_name
        HintType.BIRTHDAY -> R.string.birthday
        HintType.GENDER -> R.string.gender
        HintType.JEMALI -> R.string.jemali
    }
}