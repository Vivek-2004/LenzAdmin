package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fitting.lenz.findShopName
import com.fitting.lenz.formDate
import com.fitting.lenz.formatPaymentStatus
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.GroupOrder
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.toIST

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

    val shopOrdersList: List<GroupOrder> = lenzViewModel.groupOrders.filter { it.userId == shopId && it.trackingStatus != "Order Completed" }
    val shopsList by lenzViewModel::shopsList

    if (shopOrdersList.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                text = "No Pending Orders",
                fontStyle = FontStyle.Italic,
                color = colorScheme.compColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .drawBehind {
                    val elementHeight = this.size.height / listState.layoutInfo.totalItemsCount
                    val offset = listState.layoutInfo.visibleItemsInfo.first().index * elementHeight
                    val scrollbarHeight = listState.layoutInfo.visibleItemsInfo.size * elementHeight
                    drawRect(
                        color = colorScheme.compColor.copy(alpha = 0.5f),
                        topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                        size = Size(scrollBarWidth.toPx(), scrollbarHeight)
                    )
                }.padding(end = scrollBarWidth)
        ) {
            itemsIndexed(shopOrdersList) { index, item ->
                GroupOrderItem(
                    colorScheme = colorScheme,
                    orderId = item.id.takeLast(5),
                    shopName = item.userId.findShopName(shopsList),
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

                HorizontalDivider(
                    modifier = Modifier.width(330.dp),
                    color = Color.Green.copy(alpha = 0.45f),
                    thickness = 2.dp
                )
            }
        }
    }
}