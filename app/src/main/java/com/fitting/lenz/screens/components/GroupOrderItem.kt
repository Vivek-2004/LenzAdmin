package com.fitting.lenz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    colorScheme: ColorSchemeModel,
    orderId: String,
    shopName: String,
    orderValue: Int,
    paymentStatus: String,
    orderQuantity: Int,
    orderTime: String,
    orderStatus: String,
    orderDate: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(colorScheme.bgColor)
            .height(135.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f)
            ) {
                Text(
                    text = "#$orderId - $shopName",
                    fontSize = 13.sp,
                    color = colorScheme.compColor,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
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
                        text = orderTime,
                        color = colorScheme.compColor,
                        fontSize = 16.sp
                    )

                    Text(
                        modifier = Modifier.padding(bottom = 5.dp),
                        text = orderStatus,
                        color = Color.Yellow.copy(alpha = 0.6f),
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