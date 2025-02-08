package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
    inSelectionMode: Boolean
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 5.dp
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    var selectedIds by remember(orderGroups) { mutableStateOf(emptySet<String>()) }

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
            .background(colorScheme.bgColor.copy(alpha = 0.1f))
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
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
            itemsIndexed(orderGroups.reversed()) { index, item ->
                val selected by remember {
                    derivedStateOf {
                        selectedIds.contains(item.id)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                GroupOrderItem(
                    isItemSelected = selected,
                    colorScheme = colorScheme,
                    orderId = item.id,
                    shopName = lenzViewModel.shopsList.filter { item.userId == it._id }[0].shopName,
                    orderValue = item.finalAmount,
                    orderQuantity = item.orders.size,
                    orderTime = item.createdAt.toIST(),
                    orderDate = item.createdAt.formDate(),
                    paymentStatus = formatPaymentStatus(item.paymentStatus),
                    orderStatus = item.trackingStatus,
                    modifier = if (inSelectionMode) {
                        if (item.trackingStatus == "Work Completed") {
                            Modifier.clickable {
                                if (selected) {
                                    selectedIds = selectedIds - item.id
                                } else {
                                    selectedIds = selectedIds + item.id
                                }
                            }
                        } else {
                            Modifier
                        }
                    } else {
                        Modifier.combinedClickable(
                            onClick = {
                                navController.navigate(NavigationDestination.SingleOrderItemHolder.name + "/${item.id}")
                            },
                            onLongClick = {
                                if (item.trackingStatus == "Work Completed") {
                                    selectedIds = selectedIds + item.id
                                }
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    }
}