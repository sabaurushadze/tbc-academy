package com.tbc.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkAppColors
        else -> lightAppColors
    }

    CompositionLocalProvider(
        LocalColor provides colorScheme,
        LocalTypography provides AppTypography()

    ) {
        MaterialTheme(
            typography = Typography(),
            content = content
        )
    }
}