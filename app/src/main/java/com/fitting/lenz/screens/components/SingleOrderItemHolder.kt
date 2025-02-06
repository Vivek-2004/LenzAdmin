package com.fitting.lenz.screens.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.formDate
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.GroupOrder
import com.fitting.lenz.navigation.NavigationDestination
import com.fitting.lenz.toIST

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleOrderItemHolder(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController,
    groupOrderId: String,
    onCompleteWorkPress: () -> Unit
) {
    val listState = rememberLazyListState()
    val scrollBarWidth = 5.dp

    val orderGroups: List<GroupOrder> = lenzViewModel.groupOrders.filter { it.id == groupOrderId }
    var trackingStatus by remember { mutableStateOf(orderGroups[0].trackingStatus) }

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
                shopName = lenzViewModel.shopsList.filter { item.userId == it._id }[0].shopName,
                orderAmount = item.totalAmount,
                orderDate = item.createdAt.formDate(),
                orderTime = item.createdAt.toIST(),
                onClick = {
                    navController.navigate(NavigationDestination.OrderDetails.name + "/${item.id}")
                }
            )

            HorizontalDivider(
                modifier = Modifier.width(320.dp),
                color = colorScheme.compColor.copy(alpha = 0.15f),
                thickness = 1.dp
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(trackingStatus == "Order Received By Admin") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.DarkGray)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        onCompleteWorkPress()
                        lenzViewModel.groupOrders.filter { it.id == groupOrderId }[0].trackingStatus = "Work Complete"
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Mark Group as Complete", //trackingStatus
                    color = colorScheme.compColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(28.dp)
                        .padding(top = 3.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.complete),
                    contentDescription = "Mark Group as Complete",
                    tint = colorScheme.compColor
                )
            }
        }
        else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.DarkGray)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = trackingStatus,
                    color = colorScheme.compColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}