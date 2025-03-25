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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.AdminProfile
import com.fitting.lenz.screens.Edit
import com.fitting.lenz.screens.Orders
import com.fitting.lenz.screens.Riders
import com.fitting.lenz.screens.Shops
import com.fitting.lenz.screens.components.ShopOrdersHolder
import com.fitting.lenz.screens.components.SingleOrderItemHolder
import com.fitting.lenz.screens.details_screen.ActiveOrders
import com.fitting.lenz.screens.details_screen.FittingEdit
import com.fitting.lenz.screens.details_screen.History
import com.fitting.lenz.screens.details_screen.OrderDetails
import com.fitting.lenz.screens.details_screen.RiderDetails
import com.fitting.lenz.screens.details_screen.ShiftingEdit
import com.fitting.lenz.screens.details_screen.ShopDetails
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(
    colorScheme: ColorSchemeModel,
    lenzViewModelInstance: LenzViewModel
) {
    val adminState by lenzViewModelInstance.adminDetails.collectAsState()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(10000)
        isLoading = false
    }

    if (adminState == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    strokeWidth = 11.dp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            } else {
                Text("Some Error Occurred")
            }
        }
    } else {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        var currentScreen = currentBackStackEntry?.destination?.route

        var showBottomBar by remember { mutableStateOf(false) }
        showBottomBar = (currentScreen == NavigationDestination.Shops.name ||
                currentScreen == NavigationDestination.Orders.name ||
                currentScreen == NavigationDestination.Riders.name ||
                currentScreen == NavigationDestination.Edit.name)

        Scaffold(
            topBar = {
                TopAppBar(
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
                        navController = navController,
                        onTitleChange = {
                            currentScreen = it
                        }
                    )
                }
            },
            containerColor = colorScheme.compColor
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = NavigationDestination.Orders.name
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

                composable(route = NavigationDestination.Admin.name) {
                    AdminProfile(
                        lenzViewModel = lenzViewModelInstance
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

                composable(NavigationDestination.SingleOrderItemHolder.name + "/{groupOrderId}") { backStackEntry ->
                    val groupOrderId: String =
                        backStackEntry.arguments?.getString("groupOrderId") ?: ""
                    SingleOrderItemHolder(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController,
                        groupOrderId = groupOrderId,
                        onCompleteWorkPress = {
                            lenzViewModelInstance.patchWorkCompleted(groupOrderId)
                        }
                    )
                }

                composable(NavigationDestination.OrderDetails.name + "/{orderId}") { backStackEntry ->
                    OrderDetails(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        orderId = backStackEntry.arguments?.getString("orderId") ?: ""
                    )
                }

                composable(NavigationDestination.ShopOrdersHolder.name + "/{shopId}") { backStackEntry ->
                    ShopOrdersHolder(
                        lenzViewModel = lenzViewModelInstance,
                        colorScheme = colorScheme,
                        navController = navController,
                        shopId = backStackEntry.arguments?.getString("shopId") ?: ""
                    )
                }

                composable(NavigationDestination.History.name + "/{shopId}") { backStackEntry ->
                    History(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController,
                        shopId = backStackEntry.arguments?.getString("shopId") ?: ""
                    )
                }

                composable(NavigationDestination.ShopDetails.name + "/{shopId}") { backStackEntry ->
                    ShopDetails(
                        lenzViewModel = lenzViewModelInstance,
                        colorScheme = colorScheme,
                        shopId = backStackEntry.arguments?.getString("shopId") ?: ""
                    )
                }

                composable(NavigationDestination.ActiveOrders.name) {
                    ActiveOrders(
                        lenzViewModel = lenzViewModelInstance
                    )
                }

                composable(NavigationDestination.Riders.name) {
                    Riders(
                        lenzViewModel = lenzViewModelInstance,
                        navController = navController
                    )
                }

                composable(NavigationDestination.RiderDetails.name + "/{riderId}") { riderId ->
                    RiderDetails(
                        lenzViewModel = lenzViewModelInstance,
                        riderId = riderId.arguments?.getString("riderId") ?: "",
                    )
                }
            }
        }
    }
}