package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.derivedStateOf
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
import com.fitting.lenz.formDate
import com.fitting.lenz.formatPaymentStatus
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.GroupOrder
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.toIST
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GroupOrderItemHolder(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController,
    orderGroups: List<GroupOrder>,
    onSelectedIdsChange: (Set<String>) -> Unit,
    inSelectionMode: Boolean,
    forceReset: Boolean = false
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 4.dp
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var selectedIds by remember(orderGroups) { mutableStateOf(emptySet<String>()) }

    LaunchedEffect(forceReset, inSelectionMode) {
        if (!inSelectionMode) {
            selectedIds = emptySet()
            onSelectedIdsChange(emptySet())
        }
    }

    LaunchedEffect(selectedIds) {
        onSelectedIdsChange(selectedIds)
    }

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
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .drawBehind {
                        val elementHeight = this.size.height / listState.layoutInfo.totalItemsCount
                        val offset =
                            listState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                        val scrollbarHeight =
                            listState.layoutInfo.visibleItemsInfo.size * elementHeight
                        drawRect(
                            color = colorScheme.compColor.copy(alpha = 0.5f),
                            topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                            size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                        )
                    }
                    .padding(start = 9.dp, end = scrollBarWidth + 9.dp)
            ) {
                itemsIndexed(orderGroups.reversed()) { index, item ->
                    val selected by remember(selectedIds, forceReset) {
                        derivedStateOf {
                            selectedIds.contains(item.id)
                        }
                    }

                    Spacer(modifier = Modifier.height(9.dp))
                    GroupOrderItem(
                        isItemSelected = selected,
                        colorScheme = colorScheme,
                        orderId = item.id.takeLast(5).uppercase(),
                        shopName = lenzViewModel.shopsList.firstOrNull { shop -> shop._id == item.userId }?.shopName
                            ?: "Unknown Shop",
                        orderValue = item.finalAmount,
                        orderQuantity = item.orders.size,
                        orderTime = item.createdAt.toIST(),
                        orderDate = item.createdAt.formDate(),
                        paymentStatus = formatPaymentStatus(item.paymentStatus),
                        orderStatus = item.trackingStatus,
                        modifier = if (inSelectionMode) {
                            if (item.trackingStatus == "Work Completed") {
                                Modifier.clickable {
                                    selectedIds =
                                        if (selected) selectedIds - item.id else selectedIds + item.id
                                }
                            } else {
                                Modifier
                            }
                        } else {
                            Modifier.combinedClickable(onClick = {
                                navController.navigate(NavigationDestination.SingleOrderItemHolder.name + "/${item.id}")
                            }, onLongClick = {
                                if (item.trackingStatus == "Work Completed") {
                                    selectedIds = selectedIds + item.id
                                }
                            })
                        }
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                }
            }
        }
    }
}