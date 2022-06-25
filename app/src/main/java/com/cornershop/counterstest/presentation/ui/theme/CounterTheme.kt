package com.cornershop.counterstest.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CounterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = CounterTypography,
        shapes = CounterShapes,
        content = content
    )
}

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors(
    primary = Color(0xFFFF9500),
    secondary = Color(0xFFFFFFFF),
    error = Color.Red,
    onPrimary = Color(0xFF212121),
    onSecondary = Color(0xFF888B90),
)

private val CounterTypography: Typography = Typography()

private val CounterShapes: Shapes = Shapes()
