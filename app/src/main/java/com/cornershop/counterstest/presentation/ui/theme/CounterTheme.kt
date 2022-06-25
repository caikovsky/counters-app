package com.cornershop.counterstest.presentation.ui.theme

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

internal object CounterTheme {
    val colors: CounterColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val dimensions: CounterDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}

@Composable
internal fun CounterTheme(
    colors: CounterColors = CounterTheme.colors,
    typography: Typography = CounterTheme.typography,
    dimensions: CounterDimensions = CounterTheme.dimensions,
    shapes: Shapes = CounterTheme.shapes,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography,
        LocalDimensions provides dimensions,
        LocalShapes provides shapes
    ) { content() }
}
