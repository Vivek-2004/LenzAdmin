package com.fitting.lenz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.screens.components.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Shops(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            lenzViewModel.getGroupOrders()
        }
    }

    val shopsList by lenzViewModel::shopsList
    val lazyListState = rememberLazyListState()
    val scrollBarWidth = 5.dp
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        lenzViewModel.getShopsList()
        delay(2000L)
        isRefreshing = false
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        },
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor)
    ) {
        if (shopsList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.bgColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                isRefreshing = true
                Text(
                    text = "Some Error Occurred",
                    color = colorScheme.compColor
                )
            }
        } else {
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .drawBehind {
                        val elementHeight =
                            this.size.height / lazyListState.layoutInfo.totalItemsCount
                        val offset =
                            lazyListState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                        val scrollbarHeight =
                            lazyListState.layoutInfo.visibleItemsInfo.size * elementHeight
                        drawRect(
                            color = colorScheme.compColor.copy(alpha = 0.5f),
                            topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                            size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                        )
                    }
                    .padding(end = scrollBarWidth)
            ) {
                items(shopsList) { shop ->
                    Spacer(Modifier.height(5.dp))
                    ShopItem(
                        colorScheme = colorScheme,
                        shop = shop,
                        onShopCardClick = {
                            navController.navigate(NavigationDestination.ShopDetails.name + "/${shop._id}")
                        },
                        shopsOnClick = {
                            navController.navigate(NavigationDestination.ShopOrdersHolder.name + "/${shop._id}")
                        },
                        historyOnClick = {
                            navController.navigate(NavigationDestination.History.name + "/${shop._id}")
                        }
                    )
                    Spacer(Modifier.height(5.dp))
                }
            }
        }
    }
}