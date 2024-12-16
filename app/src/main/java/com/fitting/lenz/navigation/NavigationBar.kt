package com.fitting.lenz.navigation

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
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun BottomNavigationBar(
    colorScheme: ColorSchemeModel,
    navController: NavController
) {
    val icons = listOf(
        ImageVector.vectorResource(R.drawable.orders),
        ImageVector.vectorResource(R.drawable.shops),
        ImageVector.vectorResource(R.drawable.edit)
    )
    val items = listOf(
        NavigationDestination.Orders.name,
        NavigationDestination.Shops.name,
        NavigationDestination.Edit.name
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    Column( modifier = Modifier.wrapContentSize() ) {
        HorizontalDivider(
            color = colorScheme.compColor.copy(alpha = 0.55f),
            thickness = 2.dp
        )

        NavigationBar(
            modifier = Modifier.navigationBarsPadding().height(80.dp),
            containerColor = colorScheme.bgColor
        ) {
            items.forEachIndexed { index, itemLabel ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(itemLabel) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = icons[index],
                            contentDescription = itemLabel,
                        )
                    },
                    label = {
                        Text(
                            text = itemLabel,
                            fontSize = 14.sp
                        )
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