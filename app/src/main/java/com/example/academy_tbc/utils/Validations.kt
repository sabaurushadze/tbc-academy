package com.example.academy_tbc.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import com.example.academy_tbc.R
import com.example.academy_tbc.extension.showSnackBar

object Validations {
    fun validateInputs(
        context: Context,
        view: View,
        addressTypeDropdown: String,
        addressType: String,
        address: String,
        etName: EditText,
        etAddress: EditText,
    ): Boolean = if (addressTypeDropdown.isEmpty()) {
        view.showSnackBar(context.getString(R.string.please_select_address_type))
        false
    } else if (addressType.isEmpty()) {
        etName.error = context.getString(R.string.name_cannot_be_empty)
        false
    } else if (address.isEmpty()) {
        etAddress.error = context.getString(R.string.address_cannot_be_empty)
        false
    } else {
        true
    }
}