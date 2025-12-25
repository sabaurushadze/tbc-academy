package com.example.academy_tbc.presentation.extension

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.academy_tbc.R
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.placeholder_collab,
    errorRes: Int = R.drawable.placeholder_collab,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun View.visibleIf(visible: Boolean?, gone: Boolean = true) {
    if (visible == true) {
        show()
    } else {
        if (gone) gone() else hide()
    }
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}