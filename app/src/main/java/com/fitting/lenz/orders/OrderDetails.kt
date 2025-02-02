package com.fitting.lenz.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fitting.lenz.models.ColorSchemeModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderDetails(
    colorScheme: ColorSchemeModel,
) {
    val shopName = "Vivek"
    val orderId = "12345"
    val frameType = "Full Frame"
    val amount = "69"
    val orderType = "Shifting"
    val glassType = "etc"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Invoice",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.compColor
                )
                Text(
                    text = "LenZ Spectacle Store",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "Shop Name: $shopName ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Order ID: $orderId ",
                fontSize = 18   .sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Order Value: $amount/-",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {
            Text( // Rimless - Supra
                text = "Frame Type: $frameType",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text( // Shifting - Fitting
                text = "Order Type: $orderType",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Glass Type: $glassType",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}