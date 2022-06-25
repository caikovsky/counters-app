package com.cornershop.counterstest.presentation.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
internal data class CounterDimensions(
    val quarter: Dp = 4.dp,
    val half: Dp = 8.dp,
    val one: Dp = 16.dp,
    val two: Dp = 32.dp,
)

internal val LocalDimensions = staticCompositionLocalOf { CounterDimensions() }
