package com.fitting.lenz.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    colorScheme: ColorSchemeModel,
    navController: NavController,
    currentScreenName: String
) {
    val title = when (currentScreenName) {
        "ShiftingEdit" -> "Edit Price"
        "FittingEdit" -> "Edit Price"
        "SingleOrderItemHolder/{groupOrderId}" -> "Group Details"
        "OrderDetails/{orderId}" -> "Order Details"
        "ShopOrdersHolder/{shopId}" -> "Pending Orders"
        "History/{shopId}" -> "Order History"
        "ShopDetails/{shopId}" -> "Shop Details"
        else -> currentScreenName
    }
    var showNavigationIcon by remember { mutableStateOf(false) }
    showNavigationIcon = (
            title != NavigationDestination.Orders.name &&
                    title != NavigationDestination.Shops.name &&
                    title != NavigationDestination.Edit.name
            )
    TopAppBar(
        modifier = Modifier.background(colorScheme.bgColor),
        title = {
            Column {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            end = if (showNavigationIcon) 50.dp else 20.dp
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = colorScheme.compColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 34.sp
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        ),
        navigationIcon = {
            if (showNavigationIcon) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Button",
                        tint = colorScheme.compColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}


@Composable
fun BottomNavigationBar(
    colorScheme: ColorSchemeModel,
    navController: NavController,
    onTitleChange: (String) -> Unit
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val items = listOf(
        NavigationDestination.Orders.name,
        NavigationDestination.Shops.name,
        NavigationDestination.Edit.name
    )

    Column(modifier = Modifier.wrapContentSize()) {
        HorizontalDivider(
            color = colorScheme.compColor.copy(alpha = 0.55f),
            thickness = 2.dp
        )

        NavigationBar(
            modifier = Modifier
                .navigationBarsPadding()
                .height(80.dp),
            containerColor = colorScheme.bgColor
        ) {
            items.forEach { screen ->

                val icon = when (screen) {
                    NavigationDestination.Orders.name -> ImageVector.vectorResource(R.drawable.orders)
                    NavigationDestination.Shops.name -> ImageVector.vectorResource(R.drawable.shops)
                    NavigationDestination.Edit.name -> ImageVector.vectorResource(R.drawable.edit)
                    else -> ImageVector.vectorResource(R.drawable.orders)
                }

                NavigationBarItem(
                    selected = currentDestination?.route == screen,
                    interactionSource = remember { MutableInteractionSource() },
                    icon = {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = icon,
                            contentDescription = screen,
                        )
                    },
                    label = {
                        Text(
                            text = screen,
                            fontSize = 14.sp,
                            color = colorScheme.compColor
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
                        selectedIconColor = colorScheme.compColor,
                        unselectedIconColor = Color.Gray.copy(0.5F),
                        selectedTextColor = colorScheme.compColor,
                        unselectedTextColor = Color.Gray.copy(0.5F),
                        indicatorColor = MaterialTheme.colorScheme.background.copy(0.0F)
                    )
                )
            }
        }
    }
}