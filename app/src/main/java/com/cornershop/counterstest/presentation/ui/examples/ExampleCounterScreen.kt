package com.cornershop.counterstest.presentation.ui.examples

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.ui.examples.ExampleCounterViewModel.ExampleCounterEvent
import com.cornershop.counterstest.presentation.ui.examples.ExampleCounterViewModel.ExampleState
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme
import com.cornershop.counterstest.presentation.ui.widgets.CounterTopAppBar

@Composable
internal fun ExampleCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ExampleCounterViewModel = hiltViewModel()
) {
    val drinkExamples = stringArrayResource(R.array.drinks_array)
    val foodExamples = stringArrayResource(R.array.food_array)
    val miscExamples = stringArrayResource(R.array.misc_array)
    val state by remember { viewModel }.state.collectAsState()

    Scaffold(
        backgroundColor = CounterTheme.colors.background,
        topBar = {
            CounterTopAppBar(
                navController = navController,
                titleResId = R.string.examples
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is ExampleState.Uninitialized ->
                    ExampleCounterContent(
                        modifier = modifier,
                        drinkExamples = drinkExamples,
                        viewModel = viewModel,
                        foodExamples = foodExamples,
                        miscExamples = miscExamples
                    )
                is ExampleState.Loading -> ExampleLoading(modifier = modifier)
                is ExampleState.Success -> Log.d("ExampleCounterViewModel", "Success")
                is ExampleState.Error -> Log.d("ExampleCounterViewModel", "Error")
            }
        }
    }
}

@Composable
private fun ExampleLoading(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = CounterTheme.colors.primary
        )
    }
}

@Composable
private fun ExampleCounterContent(
    modifier: Modifier,
    drinkExamples: Array<String>,
    viewModel: ExampleCounterViewModel,
    foodExamples: Array<String>,
    miscExamples: Array<String>
) {
    Column(
        modifier = modifier.padding(
            vertical = 21.dp,
            horizontal = 21.dp
        )
    ) {
        Text(
            text = stringResource(id = R.string.examples_description),
            color = CounterTheme.colors.onSecondary,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400
        )

        Spacer(modifier = modifier.height(32.dp))

        ExampleSectionHeader(
            headerResId = R.string.example_header_drink
        )

        Spacer(modifier = modifier.height(8.dp))

        HorizontalChipGroup(
            modifier = modifier.fillMaxWidth(),
            chipList = drinkExamples,
            onItemClick = { value ->
                viewModel.onEvent(ExampleCounterEvent.OnChipClick(value))
            }
        )

        Spacer(modifier = modifier.height(48.dp))

        ExampleSectionHeader(
            headerResId = R.string.example_header_food
        )

        Spacer(modifier = modifier.height(8.dp))

        HorizontalChipGroup(
            modifier = modifier.fillMaxWidth(),
            chipList = foodExamples,
            onItemClick = { value ->
                viewModel.onEvent(ExampleCounterEvent.OnChipClick(value))
            }
        )

        Spacer(modifier = modifier.height(48.dp))

        ExampleSectionHeader(
            headerResId = R.string.example_header_misc
        )

        Spacer(modifier = modifier.height(8.dp))

        HorizontalChipGroup(
            modifier = modifier.fillMaxWidth(),
            chipList = miscExamples,
            onItemClick = { value ->
                viewModel.onEvent(ExampleCounterEvent.OnChipClick(value))
            }
        )
    }
}

@Composable
private fun HorizontalChipGroup(
    modifier: Modifier = Modifier,
    spaceMargin: Dp = 16.dp,
    chipList: Array<String>,
    onItemClick: (String) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceMargin),
    ) {
        items(chipList) { item ->
            Text(
                text = item,
                modifier = modifier
                    .background(
                        color = CounterTheme.colors.primary,
                        shape = CircleShape
                    )
                    .padding(all = 10.dp)
                    .clickable { onItemClick(item) },
                color = CounterTheme.colors.onPrimary,
            )
        }
    }
}

@Composable
private fun ExampleSectionHeader(
    @StringRes headerResId: Int
) {
    Text(
        text = stringResource(id = headerResId),
        color = CounterTheme.colors.onSecondary,
        fontSize = 17.sp,
        fontWeight = FontWeight.W500
    )
}

@Preview
@Composable
private fun ExampleCounterScreenPreview() {
    CounterTheme {
        ExampleCounterScreen(
            navController = rememberNavController()
        )
    }
}
