package com.example.academy_tbc.presentation.extension

import android.content.Context
import kotlin.math.roundToInt

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).roundToInt()
}