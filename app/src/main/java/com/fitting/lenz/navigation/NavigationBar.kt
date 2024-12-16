package com.fitting.lenz.navigation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel

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

    Column( modifier = Modifier.wrapContentSize() ) {
        HorizontalDivider(
            color = colorScheme.compColor.copy(alpha = 0.55f),
            thickness = 2.dp
        )

        NavigationBar(
            modifier = Modifier.navigationBarsPadding().height(80.dp),
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
                            fontSize = 14.sp
                        )
                    },
                    onClick = {
                        onTitleChange(screen)
                        navController.navigate(screen) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
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