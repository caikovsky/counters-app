package com.cornershop.counterstest.presentation.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme

@Composable
internal fun ListCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
}

@Preview
@Composable
private fun ListCounterScreenPreview() {
    CounterTheme {
        ListCounterScreen(
            navController = rememberNavController()
        )
    }
}
