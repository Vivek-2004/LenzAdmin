package com.fitting.lenz.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.orders.OrderDetails
import com.fitting.lenz.screens.Edit
import com.fitting.lenz.screens.Orders
import com.fitting.lenz.screens.Shops
import com.fitting.lenz.screens.components.ShopOrdersHolder
import com.fitting.lenz.screens.components.SingleOrderItemHolder
import com.fitting.lenz.screens.details_screen.FittingEdit
import com.fitting.lenz.screens.details_screen.History
import com.fitting.lenz.screens.details_screen.ShiftingEdit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(
    colorScheme: ColorSchemeModel
) {
    val lenzViewModelInstance: LenzViewModel = viewModel()

    if (lenzViewModelInstance.shopsList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.bgColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                strokeWidth = 11.dp,
                color = colorScheme.compColor.copy(alpha = 0.8f)
            )
        }
    } else {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        var currentScreen = currentBackStackEntry?.destination?.route
        println(currentScreen)

        var showBottomBar by remember { mutableStateOf(false) }
        showBottomBar = (currentScreen == NavigationDestination.Shops.name ||
                currentScreen == NavigationDestination.Orders.name ||
                currentScreen == NavigationDestination.Edit.name)

        Scaffold(
            topBar = {
                TopAppBar(
                    colorScheme = colorScheme,
                    navController = navController,
                    currentScreenName = currentScreen ?: ""
                )
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
                composable(route = NavigationDestination.Orders.name) {
                    Orders(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController
                    )
                }

                composable(route = NavigationDestination.Shops.name) {
                    Shops(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController
                    )
                }

                composable(route = NavigationDestination.Edit.name) {
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

                composable(NavigationDestination.SingleOrderItemHolder.name + "/{groupOrderId}" ) { backStackEntry ->
                    SingleOrderItemHolder(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController,
                        groupOrderId = backStackEntry.arguments?.getString("groupOrderId") ?: ""
                    )
                }

                composable(NavigationDestination.OrderDetails.name) {
                    OrderDetails(
                        colorScheme = colorScheme,
                    )
                }

                composable(NavigationDestination.ShopOrdersHolder.name + "/{shopId}" ) { backStackEntry ->
                    ShopOrdersHolder(
                        lenzViewModel = lenzViewModelInstance,
                        colorScheme = colorScheme,
                        navController = navController,
                        shopId = backStackEntry.arguments?.getString("shopId") ?: ""
                    )
                }

                composable(NavigationDestination.History.name + "/{shopId}" ) { backStackEntry ->
                    History(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController,
                        shopId = backStackEntry.arguments?.getString("shopId") ?: ""
                    )
                }

            }
        }
    }
}