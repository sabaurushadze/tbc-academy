package com.example.academy_tbc.extensions

import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import kotlin.text.trim

fun EditText.toText(): String = this.text.toString().trim()

fun View.showSnackBar(title: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, title, duration).show()
}