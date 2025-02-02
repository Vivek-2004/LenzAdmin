package com.fitting.lenz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    orderDate: String,
    onClick: () -> Unit
) {
    val orderStates = listOf("Pending", "Out for Pickup", "Pickup Received", "Out for Delivery", "Delivered")
    var orderSelectedItem by remember { mutableStateOf(orderStates[0]) }
    var expanded by remember { mutableStateOf(false) }

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
                        text = "$orderValue/- [$orderQuantity']",
                        color = colorScheme.compColor,
                        fontSize = 16.sp
                    )
                    Text(
                        text = paymentStatus,
                        color = colorScheme.compColor,
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

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100f))
                            .clickable { expanded = true }
                            .background(Color.LightGray)
                            .padding(horizontal = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = orderSelectedItem,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                        Icon(
                            imageVector = if(expanded) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            },
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.wrapContentSize(Alignment.TopStart)
                    ) {
                        orderStates.forEach { orderStateItem ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = orderStateItem,
                                        color = colorScheme.compColor
                                    ) },
                                onClick = {
                                    expanded = false
                                    orderSelectedItem = orderStateItem
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}