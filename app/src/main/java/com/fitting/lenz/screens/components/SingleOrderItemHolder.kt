package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.findShopName
import com.fitting.lenz.formDate
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.GroupOrder
import com.fitting.lenz.models.Order
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.toIST

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleOrderItemHolder(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController,
    groupOrderId: String
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 5.dp

    val orderGroups: List<GroupOrder> = lenzViewModel.groupOrders.filter { it.id == groupOrderId }
    val shopsList by lenzViewModel::shopsList

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
        itemsIndexed(orderGroups[0].orders) { index, item->
            SingleOrderItem(
                colorScheme = colorScheme,
                orderId = item.id,
                shopName = item.userId.findShopName(shopsList),
                orderAmount = item.totalAmount,
                orderDate = item.createdAt.formDate(),
                orderTime = item.createdAt.toIST(),
                onClick = {
                    navController.navigate(NavigationDestination.OrderDetails.name)
                }
            )

            HorizontalDivider(
                modifier = Modifier.width(320.dp),
                color = colorScheme.compColor.copy(alpha = 0.15f),
                thickness = 1.dp
            )
        }
    }
}