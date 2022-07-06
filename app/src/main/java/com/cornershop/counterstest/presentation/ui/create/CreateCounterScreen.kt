package com.cornershop.counterstest.presentation.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.ui.main.MainActivity.Routes
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

                val builder = AnnotatedString.Builder() // builder to attach metadata(link)
                builder.append(stringResource(id = R.string.create_counter_disclaimer)) // load current text into the builder
                val finalString = builder.toAnnotatedString()
                ClickableText(
                    text = finalString,
                    onClick = {
                        navController.navigate(Routes.ExampleCounter.route)
                    }
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
