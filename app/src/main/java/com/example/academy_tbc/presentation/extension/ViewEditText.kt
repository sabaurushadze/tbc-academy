package com.example.academy_tbc.presentation.extension

import android.widget.EditText

fun EditText.setTextIfDifferent(newText: String) {
    if (text.toString() != newText) setText(newText)
}