package com.fitting.lenz.screens.components

import android.graphics.Color.parseColor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.formDate
import com.fitting.lenz.formatPaymentStatus
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
    val singleGroupOrder: GroupOrder = lenzViewModel.groupOrders.filter { it.id == groupOrderId }[0]
    val trackingStatus by remember { mutableStateOf(singleGroupOrder.trackingStatus) }
    val statusCodeColor = when(trackingStatus) {
        "Order Placed For Pickup" -> MaterialTheme.colorScheme.onErrorContainer
        "Pickup Accepted" -> Color.Blue
        "Order Picked Up" -> Color.Green.copy(alpha = 0.5f)
        "Order Received By Admin" -> Color(parseColor("#A020F0"))
        "Work Completed" -> Color.Magenta
        "Out For Delivery" -> Color.Blue.copy(alpha = 0.6f)
        "Order Completed" -> Color.Green
        else -> colorScheme.compColor
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = colorScheme.bgColor.copy(alpha = 0.7f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 13.dp, end = 13.dp, bottom = 6.dp, top = 4.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column( modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray.copy(alpha = 0.7f))
                .padding(vertical = 6.dp, horizontal = 14.dp)
            ) {
                Text(
                    text = "#$groupOrderId",
                    fontSize = 12.sp,
                    color = colorScheme.compColor.copy(alpha = 0.7f),
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Shop Name: ")
                        }
                        append(lenzViewModel.shopsList.filter { singleGroupOrder.userId == it._id }[0].shopName)
                    },
                    fontSize = 15.sp,
                    color = colorScheme.compColor
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("No. Of Orders: ")
                        }
                        append(singleGroupOrder.orders.size.toString())
                    },
                    fontSize = 15.sp,
                    color = colorScheme.compColor
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Group Order Value: ")
                        }
                        append("Rs.${singleGroupOrder.finalAmount}/-")
                    },
                    fontSize = 15.sp,
                    color = colorScheme.compColor
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Delivery Charges Paid: ")
                        }
                        append("Rs.${singleGroupOrder.deliveryCharge}/- ")
                    },
                    fontSize = 15.sp,
                    color = colorScheme.compColor
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Order Amount ${formatPaymentStatus(singleGroupOrder.paymentStatus)}: ")
                        }
                        append("Rs.${singleGroupOrder.totalAmount}/- ")
                    },
                    fontSize = 15.sp,
                    color = ( if(formatPaymentStatus(singleGroupOrder.paymentStatus) == "Paid") Color.Green
                    else Color.Red ).copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Orders List",
            fontSize = 30.sp,
            fontWeight = FontWeight.W600
        )
        Spacer(modifier = Modifier.height(8.dp))

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
                }.padding(end = scrollBarWidth + 8.dp, start = 8.dp)
        ) {
            itemsIndexed(singleGroupOrder.orders) { index, item ->
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

                Spacer(modifier = Modifier.height(12.dp))
            }
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
                    color = statusCodeColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(28.dp)
                        .padding(top = 3.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.complete),
                    contentDescription = "Mark Group as Complete",
                    tint = statusCodeColor
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
                    color = statusCodeColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}