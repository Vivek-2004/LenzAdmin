package com.fitting.lenz.screens.components

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val selectionColor = remember(isItemSelected) {
        if (isItemSelected) Color.Gray else colorScheme.bgColor
    }

    val statusCodeColor = when(orderStatus) {
        "Order Placed For Pickup" -> MaterialTheme.colorScheme.onErrorContainer
        "Pickup Accepted" -> Color.Blue
        "Order Picked Up" -> Color.Green.copy(alpha = 0.5f)
        "Order Received By Admin" -> Color(parseColor("#A020F0"))
        "Work Completed" -> Color.Magenta
        "Out For Delivery" -> Color.Blue.copy(alpha = 0.6f)
        "Order Completed" -> Color.Green
        else -> colorScheme.compColor
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
            .background(selectionColor)
            .height(150.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 13.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().weight(.45f)
            ) {
                Text(
                    text = "#$orderId",
                    fontSize = 12.sp,
                    color = colorScheme.compColor.copy(alpha = 0.6f),
                    fontWeight = FontWeight.ExtraLight,
                    fontStyle = FontStyle.Italic
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth().weight(.65f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = shopName,
                    fontSize = 16.sp,
                    color = colorScheme.compColor,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .weight(2f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = "Rs.$orderValue/- [$orderQuantity']",
                        color = colorScheme.compColor,
                        fontSize = 16.sp
                    )
                    Text(
                        text = paymentStatus,
                        color = if (paymentStatus == "Paid") Color.Green.copy(alpha = 0.6f)
                        else Color.Red.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = "$orderDate - $orderTime",
                        color = colorScheme.compColor,
                        fontSize = 12.sp
                    )

                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = orderStatus,
                        color = statusCodeColor,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}