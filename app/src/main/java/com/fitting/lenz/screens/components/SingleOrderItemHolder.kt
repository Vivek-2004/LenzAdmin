package com.fitting.lenz.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
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
    val scrollBarWidth = 4.dp
    val singleGroupOrder: GroupOrder = lenzViewModel.groupOrders.filter { it.id == groupOrderId }[0]
    var trackingStatus by remember { mutableStateOf(singleGroupOrder.trackingStatus) }
    var updateGroupOrders by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val statusCodeColor = when (trackingStatus) {
        "Order Placed For Pickup" -> MaterialTheme.colorScheme.onErrorContainer
        "Pickup Accepted" -> Color(0xFF1A73E8)
        "Order Picked Up" -> Color(0xFF4CAF50)
        "Order Received By Admin" -> Color("#A020F0".toColorInt())
        "Work Completed" -> Color(0xFFE91E63)
        "Out For Delivery" -> Color(0xFF0D47A1)
        "Order Completed" -> Color(0xFF388E3C)
        else -> colorScheme.compColor
    }

    LaunchedEffect(updateGroupOrders) {
        if (!updateGroupOrders) return@LaunchedEffect
        try {
            lenzViewModel.getGroupOrders()
        } finally {
            updateGroupOrders = false
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = Color.White,
            title = {
                Text(
                    text = "Complete Work",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = statusCodeColor
                )
            },
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to mark this group order as complete?",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This action cannot be undone.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontStyle = FontStyle.Italic
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onCompleteWorkPress()
                        trackingStatus = "Work Complete"
                        lenzViewModel.groupOrders.filter { it.id == groupOrderId }[0].trackingStatus =
                            "Work Complete"
                        updateGroupOrders = true
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = statusCodeColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Confirm", fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "Cancel", fontSize = 16.sp, color = Color.Gray)
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorScheme.bgColor.copy(alpha = 0.95f),
                            colorScheme.bgColor.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Start),
                        shape = RoundedCornerShape(16.dp),
                        color = statusCodeColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "#${groupOrderId.takeLast(5)}",
                            color = statusCodeColor,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.7.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.complete),
                            contentDescription = "Shop",
                            tint = statusCodeColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = lenzViewModel.shopsList.filter { singleGroupOrder.userId == it._id }[0].shopName,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OrderInfoRow(
                        label = "No. Of Orders",
                        value = singleGroupOrder.orders.size.toString()
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )

                    OrderInfoRow(
                        label = "Group Order Value",
                        value = "₹${singleGroupOrder.finalAmount}/-"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OrderInfoRow(
                        label = "Delivery Charges",
                        value = "₹${singleGroupOrder.deliveryCharge}/-"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order Amount",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val paymentStatus = formatPaymentStatus(singleGroupOrder.paymentStatus)
                            val statusColor =
                                if (paymentStatus == "Paid") Color.Green else Color.Red

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = statusColor.copy(alpha = 0.1f),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(
                                    text = paymentStatus,
                                    color = statusColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }

                            Text(
                                text = "₹${singleGroupOrder.totalAmount}/-",
                                fontSize = 16.sp,
                                color = statusColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = statusCodeColor.copy(alpha = 0.3f)
                )

                Text(
                    text = "Orders List",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusCodeColor,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = statusCodeColor.copy(alpha = 0.3f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .drawBehind {
                        val scrollbarHeight = this.size.height *
                                (listState.layoutInfo.visibleItemsInfo.size.toFloat() /
                                        listState.layoutInfo.totalItemsCount.coerceAtLeast(1))

                        val scrollPosition = if (listState.layoutInfo.totalItemsCount > 0) {
                            listState.firstVisibleItemIndex.toFloat() /
                                    listState.layoutInfo.totalItemsCount *
                                    (this.size.height - scrollbarHeight)
                        } else 0f

                        if (listState.layoutInfo.totalItemsCount > 0) {
                            drawRoundRect(
                                color = statusCodeColor.copy(alpha = 0.3f),
                                topLeft = Offset(
                                    this.size.width - scrollBarWidth.toPx(),
                                    scrollPosition
                                ),
                                size = Size(scrollBarWidth.toPx(), scrollbarHeight),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                                    scrollBarWidth.toPx() / 2
                                )
                            )
                        }
                    }
                    .padding(horizontal = 16.dp)
            ) {
                itemsIndexed(singleGroupOrder.orders.reversed()) { index, item ->
                    SingleOrderItem(
                        colorScheme = colorScheme,
                        orderId = item.id,
                        orderAmount = item.totalAmount,
                        orderDate = item.createdAt.formDate(),
                        orderTime = item.createdAt.toIST(),
                        onClick = {
                            navController.navigate(NavigationDestination.OrderDetails.name + "/${item.id}")
                        }
                    )
                }
            }
        }

        if (trackingStatus == "Order Received By Admin") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(28.dp)
                        ),
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = statusCodeColor
                    )
                ) {
                    Text(
                        text = "Mark Group as Complete",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.complete),
                        contentDescription = "Complete",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = statusCodeColor.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(statusCodeColor)
                                .padding(end = 8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = trackingStatus,
                            color = statusCodeColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.DarkGray
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}