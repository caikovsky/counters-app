package com.cornershop.counterstest.presentation.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cornershop.counterstest.presentation.ui.theme.CounterTheme

@Composable
internal fun CounterTopAppBar(
    backgroundColor: Color = CounterTheme.colors.secondary,
    contentColor: Color = CounterTheme.colors.primary,
    @StringRes titleResId: Int,
    navController: NavController
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        title = { Text(text = stringResource(id = titleResId)) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        elevation = 4.dp
    )
}
