package com.fitting.lenz.screens.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun GroupOrderItem(
    modifier: Modifier = Modifier,
    colorScheme: ColorSchemeModel,
    orderId: String,
    shopName: String,
    orderValue: Int,
    paymentStatus: String,
    orderQuantity: Int,
    orderTime: String,
    orderStatus: String,
    orderDate: String,
    isItemSelected: Boolean = false
) {
    val statusCodeColor = when (orderStatus) {
        "Order Placed For Pickup" -> Color(0xFFFF6B6B)
        "Pickup Accepted" -> Color(0xFF4EA8DE)
        "Order Picked Up" -> Color(0xFF4BD37B)
        "Order Received By Admin" -> Color(0xFFB160FF)
        "Work Completed" -> Color(0xFFFF66D9)
        "Out For Delivery" -> Color(0xFF3E8FFF)
        "Order Completed" -> Color(0xFF22DD88)
        else -> colorScheme.compColor
    }

    val bgColor by animateColorAsState(
        targetValue = if (isItemSelected) Color(0xFF3E8FFF).copy(alpha = 0.1f) else Color.White,
        animationSpec = tween(300),
        label = "backgroundColorAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(7.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp))
                .background(statusCodeColor)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)
        ) {
            Text(
                text = "#$orderId",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                letterSpacing = 0.7.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${orderDate.substring(0, 5)} | $orderTime",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = shopName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DetailItem(
                    label = "Amount",
                    value = "Rs.$orderValue"
                )

                DetailItem(
                    label = "Quantity",
                    value = "$orderQuantity items"
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (paymentStatus == "Paid")
                                Color(0xFF22DD88).copy(alpha = 0.15f)
                            else
                                Color(0xFFFF6B6B).copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = paymentStatus,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (paymentStatus == "Paid")
                            Color(0xFF22DD88)
                        else
                            Color(0xFFFF6B6B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusCodeColor.copy(alpha = 0.12f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = orderStatus,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = statusCodeColor
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )
    }
}