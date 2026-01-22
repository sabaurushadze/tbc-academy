package com.example.academy_tbc.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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
            typography = androidx.compose.material3.Typography(),
            content = content
        )
    }
}