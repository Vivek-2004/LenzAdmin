package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.screens.components.ActiveOrderCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveOrders(
    lenzViewModel: LenzViewModel
) {
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(showLoading) {
        if (!showLoading) return@LaunchedEffect
        delay(1300)
        showLoading = false
    }

    LaunchedEffect(!isLoading) {
        if (!isLoading) return@LaunchedEffect
        lenzViewModel.getActiveOrders()
        isLoading = false
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        isLoading = true
        delay(1000)
        isRefreshing = false
    }

    val orders = lenzViewModel.activeOrdersList

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        }, modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        if (orders.isEmpty()) {
            showLoading = true
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (showLoading) {
                        CircularProgressIndicator()
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "No Orders",
                            modifier = Modifier
                                .size(140.dp)
                                .alpha(0.6f),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "No Active Orders",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Pull down to refresh.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(orders) { order ->
                    ActiveOrderCard(order = order)
                }
            }
        }
    }
}