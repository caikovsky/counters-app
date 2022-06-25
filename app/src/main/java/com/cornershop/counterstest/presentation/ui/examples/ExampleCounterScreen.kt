package com.cornershop.counterstest.presentation.ui.examples

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun ExampleCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
}

@Preview
@Composable
private fun ExampleCounterScreenPreview() {
    MaterialTheme {
        ExampleCounterScreen(
            navController = rememberNavController()
        )
    }
}
