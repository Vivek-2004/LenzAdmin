package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.formDate
import com.fitting.lenz.formatPaymentStatus
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.GroupOrder
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.toIST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopOrdersHolder(
    lenzViewModel: LenzViewModel,
    colorScheme: ColorSchemeModel,
    navController: NavController,
    shopId: String
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 5.dp
    var updateGroupOrders by remember { mutableStateOf(false) }
    val shopOrdersList: List<GroupOrder> = lenzViewModel.groupOrders.filter { it.userId == shopId && it.trackingStatus != "Order Completed" }

    LaunchedEffect(updateGroupOrders) {
        if(!updateGroupOrders) return@LaunchedEffect
        try {
            withContext(Dispatchers.IO) {
                lenzViewModel.getGroupOrders()
            }
        } finally {
            updateGroupOrders = false
        }
    }

    if (shopOrdersList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = colorScheme.bgColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = "No Pending Orders",
                    fontStyle = FontStyle.Italic,
                    color = colorScheme.compColor,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.wrapContentSize().clickable {
                        lenzViewModel.getGroupOrders()
                    },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Click to Refresh")
                }
            }
        }
    } else {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .background(color = Color.LightGray)
                .drawBehind {
                    val elementHeight = this.size.height / listState.layoutInfo.totalItemsCount
                    val offset = listState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                    val scrollbarHeight = listState.layoutInfo.visibleItemsInfo.size * elementHeight
                    drawRect(
                        color = colorScheme.compColor.copy(alpha = 0.5f),
                        topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                        size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                    )
                }.padding(end = scrollBarWidth + 8.dp, start = 8.dp)
        ) {
            itemsIndexed(shopOrdersList.reversed()) { index, item ->
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
                    modifier = Modifier.clickable{
                        navController.navigate(NavigationDestination.SingleOrderItemHolder.name + "/${item.id}")
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}