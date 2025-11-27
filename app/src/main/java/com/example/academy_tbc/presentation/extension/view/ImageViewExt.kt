package com.example.academy_tbc.presentation.extension.view

import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.academy_tbc.R

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.user,
    errorRes: Int = R.drawable.user,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}