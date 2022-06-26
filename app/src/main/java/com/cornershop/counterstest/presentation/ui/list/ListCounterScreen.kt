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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.model.Counter
import com.cornershop.counterstest.presentation.ui.list.ListCounterViewModel.ListCounterEvent
import com.cornershop.counterstest.presentation.ui.main.MainActivity.Routes
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme
import com.cornershop.counterstest.presentation.ui.widgets.LoadingScreen

@Composable
internal fun ListCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ListCounterViewModel = hiltViewModel()
) {
    val state by remember { viewModel }.state.collectAsState()

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
        },
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
                when (state) {
                    is State.Error -> {
                        // show error screen
                    }
                    is State.Loading -> LoadingScreen(modifier = modifier)
                    is State.Success ->
                        CounterList(
                            modifier = modifier,
                            counters = (state as State.Success).data,
                            incCounter = { counter ->
                                viewModel.onEvent(
                                    ListCounterEvent.OnIncrementClick(counter)
                                )
                            },
                            decCounter = { counter ->
                                viewModel.onEvent(
                                    ListCounterEvent.OnDecrementClick(counter)
                                )
                            },
                        )
                }
            }
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
                vertical = 20.dp,
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
                text = stringResource(id = R.string.create_counter),
                color = CounterTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun CounterList(
    modifier: Modifier = Modifier,
    counters: List<Counter>,
    incCounter: (Counter) -> Unit,
    decCounter: (Counter) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(counters) { counter ->
            CounterItem(
                modifier = modifier,
                counter = counter,
                incCounter = incCounter,
                decCounter = decCounter
            )
        }
    }
}

@Composable
internal fun CounterItem(
    modifier: Modifier,
    counter: Counter,
    incCounter: (Counter) -> Unit,
    decCounter: (Counter) -> Unit
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
                onClick = { decCounter(counter) },
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
                onClick = { incCounter(counter) },
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
private fun CounterListPreview() {
    CounterTheme {
        CounterList(
            counters = listOf(
                Counter(id = "1", title = "Test 0", count = 0, selected = false),
                Counter(id = "2", title = "Test 1", count = 1, selected = false),
                Counter(id = "3", title = "Test 2", count = 2, selected = false),
            ),
            incCounter = {},
            decCounter = {}
        )
    }
}

/*@Preview
@Composable
private fun ListCounterScreenPreview() {
    CounterTheme {
        ListCounterScreen(
            navController = rememberNavController()
        )
    }
}*/
