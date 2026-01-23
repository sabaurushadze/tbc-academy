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

    val neutral1: Color = Color.Unspecified,
    val neutral2: Color = Color.Unspecified,
    val neutral3: Color = Color.Unspecified,
    val neutral4: Color = Color.Unspecified,


    val error: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified,
    val success: Color = Color.Unspecified
)

internal val lightAppColors = AppColors(
    primary = Color(0xFF2ECC71),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFF1F2E35),
    onBackground = Color(0xFFFFFFFF),

    neutral1 = Color(0xFF777E90),
    neutral2 = Color(0xFFB8C2C0),
    neutral3 = Color(0xFFEDF1FA),
    neutral4 = Color(0xFF1A282F),

    error = Color(0xFFC50000),
    success = Color(0xFF009254),
    warning = Color(0xFFCF6212)
)

internal val darkAppColors = AppColors(
    primary = Color(0xFF5A9BFF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF2C2E36),
    onBackground = Color(0xFFFFFFFF),

    neutral1 = Color(0xFF23262F),


    error = Color(0xFFE16666),
    success = Color(0xFF3ED79A),
    warning = Color(0xFFE09661)
)