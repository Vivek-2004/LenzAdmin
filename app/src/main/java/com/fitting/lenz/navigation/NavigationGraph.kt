package com.fitting.lenz.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.Edit
import com.fitting.lenz.screens.Orders
import com.fitting.lenz.screens.Shops
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MyApp(
    colorScheme: ColorSchemeModel
) {
    val navController = rememberNavController()
    val currentScreen = remember { MutableStateFlow(NavigationDestination.Shops) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                colorScheme = colorScheme,
                navController = navController,
                onTitleChange = {
                    currentScreen.value = when(it) {
                        NavigationDestination.Orders.name -> NavigationDestination.Orders
                        NavigationDestination.Shops.name -> NavigationDestination.Shops
                        NavigationDestination.Edit.name -> NavigationDestination.Edit
                        else ->NavigationDestination.Shops
                    }
                }
            )
        }
    ){ innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = NavigationDestination.Orders.name
        ) {
            composable(NavigationDestination.Orders.name){
                LaunchedEffect(Unit) {
                    currentScreen.value = NavigationDestination.Orders
                }
                Orders(colorScheme)
            }
            composable(NavigationDestination.Shops.name){
                LaunchedEffect(Unit) {
                    currentScreen.value = NavigationDestination.Shops
                }
                Shops(colorScheme)
            }
            composable(NavigationDestination.Edit.name){
                LaunchedEffect(Unit) {
                    currentScreen.value = NavigationDestination.Edit
                }
                Edit(colorScheme)
            }

//            composable(NavigationDestination.History.name){
//                History(colorScheme)
//            }
        }
    }
}