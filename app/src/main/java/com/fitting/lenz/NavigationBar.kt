package com.fitting.lenz

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBarExample() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        },

    ) { innerPadding ->
        Column(Modifier.padding(116.dp)) {
            Text(
                modifier = Modifier.padding(innerPadding),
                text = "FUCK AASHISH"
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val themeColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    val icons = listOf(
        ImageVector.vectorResource(R.drawable.orders),
        ImageVector.vectorResource(R.drawable.history),
        ImageVector.vectorResource(R.drawable.shops),
        ImageVector.vectorResource(R.drawable.edit)
    )
    val items = listOf("Orders", "History", "Shops", "Edit")
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier.navigationBarsPadding().height(80.dp)
    ) {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = icons[index],
                        contentDescription = label,
                    )
                },
                label = { Text(
                    text = label,
                    fontSize = 14.sp
                    ) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = themeColor,
                    unselectedIconColor = Color.Gray.copy(0.3F),
                    selectedTextColor = themeColor,
                    unselectedTextColor = Color.Gray.copy(0.3F),
                    indicatorColor = MaterialTheme.colorScheme.background.copy(0.0F)
                )
            )
        }
    }
}