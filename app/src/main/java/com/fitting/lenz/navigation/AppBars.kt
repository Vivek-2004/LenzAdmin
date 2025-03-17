package com.fitting.lenz.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fitting.lenz.R

@Composable
fun TopAppBar(
    navController: NavController,
    currentScreenName: String
) {
    val title = when (currentScreenName) {
        "Edit" -> "Edit Charges"
        "ShiftingEdit" -> "Edit"
        "FittingEdit" -> "Edit"
        "SingleOrderItemHolder/{groupOrderId}" -> "Group Details"
        "OrderDetails/{orderId}" -> "Order Details"
        "ShopOrdersHolder/{shopId}" -> "Pending Orders"
        "History/{shopId}" -> "Order History"
        "ShopDetails/{shopId}" -> "Shop Details"
        "ActiveOrders" -> "Active Orders"
        else -> currentScreenName
    }

    val showNavigationIcon = (
            title != NavigationDestination.Orders.name &&
                    title != NavigationDestination.Shops.name &&
                    title != "Edit Charges" &&
                    title != NavigationDestination.Admin.name
            )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(55.dp)
            .background(Color.Black)
    ) {
        if (showNavigationIcon) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp),
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(34.dp),
                    tint = Color.White
                )
            }
        }

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = if (showNavigationIcon) 64.dp else 24.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Left,
            fontFamily = FontFamily.Default
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onTitleChange: (String) -> Unit
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items = listOf(
        NavigationDestination.Orders.name,
        NavigationDestination.Shops.name,
        NavigationDestination.Edit.name,
        NavigationDestination.Admin.name
    )
    Column(modifier = Modifier.wrapContentSize()) {
        HorizontalDivider(thickness = 1.dp, color = Color.DarkGray)
        NavigationBar(
            modifier = Modifier
                .navigationBarsPadding()
                .height(80.dp),
            containerColor = Color.Black
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.route == screen
                val icon = when (screen) {
                    NavigationDestination.Orders.name -> painterResource(R.drawable.orders)
                    NavigationDestination.Shops.name -> painterResource(R.drawable.shops)
                    NavigationDestination.Edit.name -> painterResource(R.drawable.edit)
                    NavigationDestination.Admin.name -> painterResource(R.drawable.profile)
                    else -> painterResource(R.drawable.orders)
                }
                NavigationBarItem(
                    selected = selected,
                    interactionSource = remember { MutableInteractionSource() },
                    icon = {
                        Icon(
                            modifier = if (selected) Modifier.size(30.dp) else Modifier.size(25.dp),  // Slightly reduced size
                            painter = icon,
                            contentDescription = screen,
                            tint = Color.Unspecified
                        )
                    },
                    label = {
                        Text(
                            text = screen,
                            fontSize = if (selected) 16.sp else 13.5.sp,
                            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal,
                            color = if (selected) Color.White else Color.LightGray.copy(alpha = 0.7f)
                        )
                    },
                    onClick = {
                        onTitleChange(screen)
                        navController.navigate(screen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.LightGray.copy(alpha = 0.7f),
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.LightGray.copy(alpha = 0.7f),
                        indicatorColor = Color.Gray
                    )
                )
            }
        }
    }
}