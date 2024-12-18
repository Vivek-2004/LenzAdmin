package com.fitting.lenz.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.Edit
import com.fitting.lenz.screens.Orders
import com.fitting.lenz.screens.Shops
import com.fitting.lenz.screens.details_screen.FittingEdit
import com.fitting.lenz.screens.details_screen.ShiftingEdit

@Composable
fun MyApp(
    colorScheme: ColorSchemeModel
) {
    val lenzViewModelInstance: LenzViewModel = viewModel()

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var currentScreen = currentBackStackEntry?.destination?.route

    var showBottomBar by remember { mutableStateOf(false) }
    showBottomBar = currentScreen == NavigationDestination.Shops.name ||
            currentScreen == NavigationDestination.Orders.name ||
            currentScreen == NavigationDestination.Edit.name

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !showBottomBar,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 500),
                    expandFrom = Alignment.Top
                ) + fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 500),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                TopAppBar(
                    colorScheme = colorScheme,
                    navController = navController,
                    currentScreenName = currentScreen ?: ""
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 500),
                    expandFrom = Alignment.Top
                ) + fadeIn(animationSpec = tween(durationMillis = 500)),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 500),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                BottomNavigationBar(
                    colorScheme = colorScheme,
                    navController = navController,
                    onTitleChange = {
                        currentScreen = it
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavigationDestination.Shops.name
        ) {
            composable(NavigationDestination.Orders.name) {
                Orders(
                    colorScheme = colorScheme,
                    lenzViewModel = lenzViewModelInstance
                )
            }
            composable(NavigationDestination.Shops.name) {
                Shops(
                    colorScheme = colorScheme,
                    lenzViewModel = lenzViewModelInstance
                )
            }
            composable(NavigationDestination.Edit.name) {
                Edit(
                    colorScheme = colorScheme,
                    navController = navController
                )
            }
            composable(NavigationDestination.ShiftingEdit.name) {
                ShiftingEdit(
                    colorScheme = colorScheme,
                    lenzViewModel = lenzViewModelInstance
                )
            }
            composable(NavigationDestination.FittingEdit.name) {
                FittingEdit(
                    colorScheme = colorScheme,
                    lenzViewModel = lenzViewModelInstance
                )
            }
        }
    }
}