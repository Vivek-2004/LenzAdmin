package com.fitting.lenz.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.Edit
import com.fitting.lenz.screens.components.History
import com.fitting.lenz.screens.Orders
import com.fitting.lenz.screens.Shops

@Composable
fun MyApp(
    colorScheme: ColorSchemeModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                colorScheme = colorScheme,
                navController = navController
            )
        }
    ){ innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavigationDestination.Orders.name
        ) {
            composable(NavigationDestination.Orders.name){
                Orders(colorScheme)
            }
            composable(NavigationDestination.Shops.name){
                Shops(colorScheme)
            }
            composable(NavigationDestination.Edit.name){
                Edit(colorScheme)
            }


            composable(NavigationDestination.History.name){
                History(colorScheme)
            }
        }
    }
}