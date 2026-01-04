package com.example.academy_tbc.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

sealed interface GenericString {
    data class DynamicString(val value: String) : GenericString
    data class StringResource(@param:StringRes val stringRes: Int) : GenericString

    @Composable
    fun getString(): String {
        val context = LocalContext.current
        return getString(context)
    }

    fun getString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(stringRes)
        }
    }
}