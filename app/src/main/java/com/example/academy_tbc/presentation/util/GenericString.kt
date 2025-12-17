package com.example.academy_tbc.presentation.util

import android.content.Context
import androidx.annotation.StringRes

sealed interface GenericString {
    data class DynamicString(val value: String) : GenericString
    data class StringResource(@param:StringRes val resId: Int) : GenericString

    fun getString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
        }
    }
}



