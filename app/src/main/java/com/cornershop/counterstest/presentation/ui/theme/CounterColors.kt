package com.cornershop.counterstest.presentation.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LightColorPalette = CounterColors(
    background = Color.White,
    primary = Color(0xFFFF9500),
    secondary = Color(0xFFFFFFFF),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF212121),
    onSecondary = Color(0xFF888B90),
    isLight = true
)

internal class CounterColors(
    background: Color,
    primary: Color,
    secondary: Color,
    onPrimary: Color,
    onSecondary: Color,
    error: Color,
    isLight: Boolean
) {
    var background by mutableStateOf(background)
        private set
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var error by mutableStateOf(error)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        background: Color = this.background,
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        onPrimary: Color = this.onPrimary,
        onSecondary: Color = this.onSecondary,
        error: Color = this.error,
        isLight: Boolean = this.isLight
    ): CounterColors = CounterColors(
        background,
        primary,
        secondary,
        onPrimary,
        onSecondary,
        error,
        isLight
    )

    fun updateColorsFrom(other: CounterColors) {
        background = other.background
        primary = other.primary
        secondary = other.secondary
        onPrimary = other.onPrimary
        onSecondary = other.onSecondary
        error = other.error
        isLight = other.isLight
    }
}

internal val LocalColors = staticCompositionLocalOf { LightColorPalette }
