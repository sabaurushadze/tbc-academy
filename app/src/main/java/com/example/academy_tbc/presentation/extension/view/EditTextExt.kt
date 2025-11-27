package com.example.academy_tbc.presentation.extension.view

import android.widget.EditText

fun EditText.setTextIfDifferent(newText: String) {
    if (text.toString() != newText) setText(newText)
}