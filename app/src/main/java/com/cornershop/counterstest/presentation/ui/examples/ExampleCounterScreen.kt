package com.cornershop.counterstest.presentation.ui.examples

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme
import com.cornershop.counterstest.presentation.ui.widgets.CounterTopAppBar

@Composable
internal fun ExampleCounterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val drinkExamples = stringArrayResource(R.array.drinks_array)
    val foodExamples = stringArrayResource(R.array.food_array)
    val miscExamples = stringArrayResource(R.array.misc_array)

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
                    chipList = drinkExamples
                )

                Spacer(modifier = modifier.height(48.dp))

                ExampleSectionHeader(
                    headerResId = R.string.example_header_food
                )

                Spacer(modifier = modifier.height(8.dp))

                HorizontalChipGroup(
                    modifier = modifier.fillMaxWidth(),
                    chipList = foodExamples
                )

                Spacer(modifier = modifier.height(48.dp))

                ExampleSectionHeader(
                    headerResId = R.string.example_header_misc
                )

                Spacer(modifier = modifier.height(8.dp))

                HorizontalChipGroup(
                    modifier = modifier.fillMaxWidth(),
                    chipList = miscExamples
                )
            }
        }
    }
}

@Composable
private fun HorizontalChipGroup(
    modifier: Modifier = Modifier,
    spaceMargin: Dp = 16.dp,
    chipList: Array<String>
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
                    .padding(all = 10.dp),
                color = CounterTheme.colors.onPrimary
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
