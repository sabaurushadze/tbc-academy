package com.example.academy_tbc.presentation.extension

import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.academy_tbc.R

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_placeholder,
    errorRes: Int = R.drawable.img_ryan,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}