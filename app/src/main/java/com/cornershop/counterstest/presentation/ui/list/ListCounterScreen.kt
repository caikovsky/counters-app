package com.cornershop.counterstest.presentation.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.presentation.ui.list.ListCounterViewModel.ListCounterEvent
import com.cornershop.counterstest.presentation.ui.list.ListCounterViewModel.ListCounterState
import com.cornershop.counterstest.presentation.ui.main.MainActivity.Routes
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme
import com.cornershop.counterstest.presentation.ui.widgets.LoadingScreen

@Composable
internal fun ListCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ListCounterViewModel = hiltViewModel()
) {
    val state by remember { viewModel.state }.collectAsState()

    Scaffold(
        backgroundColor = CounterTheme.colors.background,
        topBar = {
            TopAppBar(
                backgroundColor = CounterTheme.colors.secondary,
                contentColor = CounterTheme.colors.primary,
                title = { Text(text = stringResource(id = R.string.app_name)) },
                elevation = 4.dp
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            CreateCounterFloatingActionButton(
                navController = navController,
                modifier = modifier
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
                when (val currentValue = state) {
                    is State.Error -> CounterListErrorScreen(
                        modifier = modifier,
                        onEvent = viewModel::onEvent
                    )
                    is State.Loading -> LoadingScreen(modifier = modifier)
                    is State.Success -> ListCounterContentScreen(
                        modifier = modifier,
                        state = currentValue.data,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
private fun ListCounterContentScreen(
    modifier: Modifier = Modifier,
    state: ListCounterState,
    onEvent: (ListCounterEvent) -> Unit
) {
    if (state.counters.isEmpty()) {
        CounterListEmptyScreen(modifier = modifier)
    } else {
        Column {
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.n_items, state.totalAmount),
                    color = CounterTheme.colors.onPrimary,
                    fontSize = 17.sp
                )

                Spacer(modifier.height(8.dp))

                Text(
                    modifier = modifier.padding(start = 7.dp),
                    text = stringResource(id = R.string.n_times, state.totalValue),
                    color = CounterTheme.colors.onSecondary,
                    fontSize = 17.sp
                )
            }

            Spacer(modifier.height(16.dp))

            CounterList(
                modifier = modifier,
                counters = state.counters,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun CounterListEmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.no_counters),
            textAlign = TextAlign.Center,
            color = CounterTheme.colors.onSecondary,
            fontSize = 22.sp
        )

        Spacer(modifier.height(16.dp))

        Text(
            modifier = modifier.padding(horizontal = 32.dp),
            text = stringResource(id = R.string.no_counters_phrase),
            textAlign = TextAlign.Center,
            color = CounterTheme.colors.onSecondary,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun CounterListErrorScreen(
    modifier: Modifier = Modifier,
    onEvent: (ListCounterEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_load_counters_title),
            textAlign = TextAlign.Center,
            color = CounterTheme.colors.onSecondary,
            fontSize = 22.sp
        )

        Spacer(modifier.height(16.dp))

        Text(
            modifier = modifier.padding(horizontal = 32.dp),
            text = stringResource(id = R.string.connection_error_description),
            textAlign = TextAlign.Center,
            color = CounterTheme.colors.onSecondary,
            fontSize = 18.sp
        )

        Spacer(modifier.height(16.dp))

        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            onClick = { onEvent(ListCounterEvent.OnRetryClick) }
        ) {
            Text(
                text = stringResource(id = R.string.retry).uppercase(),
                color = CounterTheme.colors.primary
            )
        }
    }
}

@Composable
private fun CreateCounterFloatingActionButton(
    navController: NavController,
    modifier: Modifier
) {
    FloatingActionButton(
        backgroundColor = CounterTheme.colors.primary,
        onClick = { navController.navigate(Routes.CreateCounters.route) }
    ) {
        Row(
            modifier = modifier.padding(
                vertical = 10.dp,
                horizontal = 20.dp
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                tint = CounterTheme.colors.secondary
            )

            Spacer(modifier.width(8.dp))

            Text(
                text = stringResource(id = R.string.create_counter).uppercase(),
                color = CounterTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun CounterList(
    modifier: Modifier = Modifier,
    counters: List<Counter>,
    onEvent: (ListCounterEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(counters) { counter ->
            CounterItem(
                modifier = modifier,
                counter = counter,
                onEvent = onEvent
            )
        }
    }
}

@Composable
internal fun CounterItem(
    modifier: Modifier,
    counter: Counter,
    onEvent: (ListCounterEvent) -> Unit
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = counter.title,
                color = CounterTheme.colors.primary,
                fontSize = 17.sp
            )
        }

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { onEvent(ListCounterEvent.OnDecrementClick(counter)) },
                enabled = counter.count > 0
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = null,
                    tint = if (counter.count > 0) CounterTheme.colors.primary else CounterTheme.colors.onSecondary
                )
            }

            Spacer(modifier = modifier.width(8.dp))

            Text(
                text = counter.count.toString(),
                color = CounterTheme.colors.onPrimary,
                fontSize = 20.sp
            )

            Spacer(modifier = modifier.width(8.dp))

            IconButton(
                onClick = { onEvent(ListCounterEvent.OnIncrementClick(counter)) },
                enabled = counter.count >= 0
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    tint = if (counter.count >= 0) CounterTheme.colors.primary else CounterTheme.colors.onSecondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun CounterListErrorScreenPreview() {
    CounterTheme {
        CounterListErrorScreen(
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun CounterListEmptyScreenPreview() {
    CounterTheme {
        CounterListEmptyScreen()
    }
}

@Preview
@Composable
private fun ListCounterContentScreenPreview() {
    CounterTheme {
        ListCounterContentScreen(
            state = ListCounterState(
                counters = listOf(
                    Counter(id = "1", title = "Test 1", count = 0, selected = false),
                    Counter(id = "2", title = "Test 2", count = 1, selected = false),
                    Counter(id = "3", title = "Test 3", count = 2, selected = false)
                ),
                totalAmount = 3,
                totalValue = 6
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun CounterListPreview() {
    CounterTheme {
        CounterList(
            counters = listOf(
                Counter(id = "4", title = "Test 4", count = 0, selected = false),
                Counter(id = "5", title = "Test 5", count = 1, selected = false),
                Counter(id = "6", title = "Test 6", count = 2, selected = false)
            ),
            onEvent = {}
        )
    }
}
