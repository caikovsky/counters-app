package com.cornershop.counterstest.presentation.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme

@Composable
internal fun CreateCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        backgroundColor = CounterTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = CounterTheme.colors.secondary,
                contentColor = CounterTheme.colors.primary,
                title = { Text(text = stringResource(id = R.string.create_counter)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = modifier.padding(
                    vertical = 21.dp,
                    horizontal = 21.dp
                )
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = stringResource(id = R.string.counter_name_label)) },
                    maxLines = 1,
                    textStyle = TextStyle(color = CounterTheme.colors.primary)
                )

                Spacer(modifier = modifier.height(25.dp))

                Text(
                    text = stringResource(id = R.string.create_counter_disclaimer),
                    color = CounterTheme.colors.onSecondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun CreateCounterScreenPreview() {
    CounterTheme {
        CreateCounterScreen(
            navController = rememberNavController()
        )
    }
}
