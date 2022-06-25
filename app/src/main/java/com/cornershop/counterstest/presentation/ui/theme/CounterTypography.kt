package com.cornershop.counterstest.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf


internal val CounterTypography: Typography = Typography()
internal val LocalTypography = staticCompositionLocalOf { CounterTypography }
