package com.example.academy_tbc.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val LocalColor = compositionLocalOf { AppColors() }

val AppColor: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColor.current

data class AppColors(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val onSurface: Color = Color.Unspecified,
    val onSurfaceContainer: Color = Color.Unspecified,
    val onSurfaceLight: Color = Color.Unspecified,

    val neutral1: Color = Color.Unspecified,

    val neutralText1: Color = Color.Unspecified,

    val error: Color = Color.Unspecified,
    val unspecified: Color = Color.Unspecified,
)

internal val lightAppColors = AppColors(
    primary = Color(0xFF0064D2),
    onPrimary = Color(0xFFFFFFFF),

    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF101010),

    surface = Color(0xFFF1F1F1),
    onSurface = Color(0xFF96A7AF),
    onSurfaceContainer = Color(0xFF2A3C44),
    onSurfaceLight = Color(0xFFFFFFFF),

    neutral1 = Color(0xFFE0E0E0),

    neutralText1 = Color(0xFF626262),

    error = Color(0xFFB61616),
    unspecified = Color.Unspecified

)

internal val darkAppColors = AppColors(
    primary = Color(0xFF5A9BFF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    neutral1 = Color(0xFFB2B2B2),
    onSurfaceLight = Color(0xFFFFFFFF),
    surface = Color(0xFF262626),
    onSurface = Color(0xFF96A7AF),

    error = Color(0xFFEA5858),
    unspecified = Color.Unspecified
)