package com.cornershop.counterstest.presentation.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.domain.usecases.TextInputUseCase.TextInputState
import com.cornershop.counterstest.presentation.ui.create.CreateCounterViewModel.*
import com.cornershop.counterstest.presentation.ui.main.MainActivity.Routes
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme

@Composable
internal fun CreateCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CreateCounterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    CreateCounterActions(state = state, navController = navController)

    Scaffold(
        backgroundColor = CounterTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = CounterTheme.colors.secondary,
                contentColor = CounterTheme.colors.primary,
                title = { Text(text = stringResource(id = R.string.create_counter)) },
                actions = {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = modifier.padding(all = 5.dp),
                            color = CounterTheme.colors.primary
                        )
                    } else {
                        TextButton(
                            onClick = {
                                viewModel.onEvent(
                                    CreateCounterEvent.OnCreateCounterClick(
                                        name = state.textInput?.text ?: ""
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.create_counter),
                                color = CounterTheme.colors.primary
                            )
                        }
                    }
                }
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
                ScreenContent(
                    textInputState = state.textInput,
                    navController = navController,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@Composable
private fun CreateCounterActions(
    state: CreateCounterState,
    navController: NavController
) {
    when (state.action) {
        is CreateCounterAction.CounterCreated -> {
            navController.navigate(Routes.ListCounters.route)
        }
        is CreateCounterAction.ShowErrorDialog -> {}
        else -> {}
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    textInputState: TextInputState?,
    navController: NavController,
    onEvent: (CreateCounterEvent) -> Unit
) {
    TextField(
        value = textInputState?.text ?: "",
        onValueChange = { value ->
            onEvent(CreateCounterEvent.OnCounterNameChange(name = value))
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = CounterTheme.colors.primary,
            cursorColor = CounterTheme.colors.primary
        ),
        label = {
            Text(
                text = stringResource(id = R.string.counter_name_label),
                color = CounterTheme.colors.primary
            )
        },
        maxLines = 1,
        textStyle = TextStyle(color = CounterTheme.colors.onPrimary)
    )

    Spacer(modifier = modifier.height(25.dp))

    val builder = AnnotatedString.Builder() // builder to attach metadata(link)
    builder.append(stringResource(id = R.string.create_counter_disclaimer)) // load current text into the builder
    val finalString = builder.toAnnotatedString()

    ClickableText(
        text = finalString,
        onClick = { navController.navigate(Routes.ExampleCounter.route) }
    )
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
