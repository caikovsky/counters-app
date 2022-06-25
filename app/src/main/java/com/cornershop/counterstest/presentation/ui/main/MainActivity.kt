package com.cornershop.counterstest.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cornershop.counterstest.presentation.ui.create.CreateCounterScreen
import com.cornershop.counterstest.presentation.ui.examples.ExampleCounterScreen
import com.cornershop.counterstest.presentation.ui.list.ListCounterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.CreateCounters.route
                ) {
                    composable(route = Routes.ListCounters.route) {
                        ListCounterScreen(
                            navController = navController
                        )
                    }
                    composable(route = Routes.CreateCounters.route) {
                        CreateCounterScreen(
                            navController = navController
                        )
                    }
                    composable(route = Routes.ExampleCounter.route) {
                        ExampleCounterScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    private sealed class Routes(val route: String) {
        object ListCounters : Routes("list_screen")
        object CreateCounters : Routes("create_screen")
        object ExampleCounter : Routes("example_screen")
    }
}
