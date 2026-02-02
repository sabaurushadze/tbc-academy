package com.tbc.home.presentation.mapper.form

import androidx.annotation.StringRes
import com.tbc.home.domain.model.form.FieldType
import com.tbc.home.domain.model.form.Form
import com.tbc.home.domain.model.form.HintType
import com.tbc.home.presentation.model.form.UiForm
import com.tbc.resource.R

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