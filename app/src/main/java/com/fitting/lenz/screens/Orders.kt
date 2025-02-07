package com.fitting.lenz.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.formDate
import com.fitting.lenz.formatPaymentStatus
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.screens.components.GroupOrderItem
import com.fitting.lenz.toIST
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Orders(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 5.dp
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val orderGroups by lenzViewModel::groupOrders

    LaunchedEffect(isRefreshing) {
        lenzViewModel.getGroupOrders()
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
            .background(colorScheme.bgColor.copy(alpha = 0.1f))
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.bgColor)
                .drawBehind {
                    val elementHeight = this.size.height / listState.layoutInfo.totalItemsCount
                    val offset = listState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                    val scrollbarHeight = listState.layoutInfo.visibleItemsInfo.size * elementHeight
                    drawRect(
                        color = colorScheme.compColor.copy(alpha = 0.5f),
                        topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                        size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                    )
                }
                .padding(end = scrollBarWidth + 8.dp, start = 8.dp)
        ) {
            itemsIndexed(orderGroups) { index, item ->
                Spacer(modifier = Modifier.height(14.dp))

                GroupOrderItem(
                    colorScheme = colorScheme,
                    orderId = item.id,
                    shopName = lenzViewModel.shopsList.filter { item.userId == it._id }[0].shopName,
                    orderValue = item.finalAmount,
                    orderQuantity = item.orders.size,
                    orderTime = item.createdAt.toIST(),
                    orderDate = item.createdAt.formDate(),
                    paymentStatus = formatPaymentStatus(item.paymentStatus),
                    orderStatus = item.trackingStatus,
                    onClick = {
                        navController.navigate(NavigationDestination.SingleOrderItemHolder.name + "/${item.id}")
                    }
                )

                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    }
}