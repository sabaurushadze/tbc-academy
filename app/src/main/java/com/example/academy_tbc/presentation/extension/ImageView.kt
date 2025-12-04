package com.example.academy_tbc.presentation.extension

import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.example.academy_tbc.R

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.loading_img,
    errorRes: Int = R.drawable.ic_broken_image,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}