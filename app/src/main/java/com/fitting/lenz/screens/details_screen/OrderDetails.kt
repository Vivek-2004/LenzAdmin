package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.checkNullorEmpty
import com.fitting.lenz.formatPaymentStatus
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.Order

@Composable
fun OrderDetails(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    orderId: String
) {
    lateinit var orderDetails: Order
    lenzViewModel.groupOrders.forEach { groupOrder ->
        groupOrder.orders.forEach { singleOrder ->
            if (singleOrder.id == orderId) {
                orderDetails = singleOrder
            }
        }
    }

    val scrollState = rememberScrollState()
    val paymentStatus = formatPaymentStatus(orderDetails.paymentStatus)
    val shopName = lenzViewModel.shopsList.filter { orderDetails.userId == it._id }[0].shopName

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 24.dp)
        ) {
            // Enhanced header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                colorScheme.compColor.copy(alpha = 0.2f),
                                colorScheme.bgColor
                            )
                        )
                    )
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LenZ Spectacle Store",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.compColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (paymentStatus == "Paid")
                                        Color(0xFF4CAF50).copy(alpha = 0.15f)
                                    else
                                        Color(0xFFE53935).copy(alpha = 0.15f)
                                )
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (paymentStatus == "Paid") Icons.Default.CheckCircle else Icons.Default.Receipt,
                                contentDescription = "Payment Status",
                                tint = if (paymentStatus == "Paid") Color(0xFF4CAF50) else Color(
                                    0xFFE53935
                                ),
                                modifier = Modifier.size(32.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "$paymentStatus Order",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (paymentStatus == "Paid") Color(0xFF4CAF50) else Color(
                                        0xFFE53935
                                    )
                                )

                                Text(
                                    text = "₹${orderDetails.totalAmount}/-",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorScheme.compColor
                                )
                            }
                        }
                    }

                    if (paymentStatus != "Paid") {
                        Text(
                            text = "[ Delivery Charges are always Paid ]",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                            color = colorScheme.compColor.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Order information section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorScheme.bgColor.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Order Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.compColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        color = colorScheme.compColor.copy(alpha = 0.2f)
                    )

                    InfoRow(
                        label = "Order ID",
                        value = "#${orderId.takeLast(10)}",
                        textColor = colorScheme.compColor
                    )

                    InfoRow(
                        label = "Shop Name",
                        value = shopName,
                        textColor = colorScheme.compColor
                    )

                    InfoRow(
                        label = "Order Type",
                        value = orderDetails.shiftingOrFitting,
                        textColor = colorScheme.compColor
                    )

                    InfoRow(
                        label = "Frame Type",
                        value = orderDetails.frameOptions.type,
                        textColor = colorScheme.compColor
                    )

                    if (orderDetails.shiftingOrFitting == "Fitting") {
                        InfoRow(
                            label = "Glass Type",
                            value = orderDetails.glassType!!,
                            textColor = colorScheme.compColor
                        )

                        InfoRow(
                            label = "Lens Type",
                            value = orderDetails.lensDetails!!,
                            textColor = colorScheme.compColor
                        )

                        if (!orderDetails.materialDetails.isNullOrEmpty()) {
                            InfoRow(
                                label = "Material Details",
                                value = orderDetails.materialDetails!!,
                                textColor = colorScheme.compColor
                            )
                        }
                    }
                }
            }

            // Power details section (only for Fitting orders)
            if (orderDetails.shiftingOrFitting == "Fitting") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorScheme.bgColor.copy(alpha = 0.5f))
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Visibility,
                                contentDescription = "Power Details",
                                tint = colorScheme.compColor,
                                modifier = Modifier.size(28.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "Power Details",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            color = colorScheme.compColor.copy(alpha = 0.2f)
                        )

                        // Right Eye
                        EyeDetailCard(
                            eyeTitle = "Right Eye",
                            spherical = orderDetails.powerDetails?.right?.spherical?.checkNullorEmpty()
                                ?: "-",
                            cylindrical = orderDetails.powerDetails?.right?.cylindrical?.checkNullorEmpty()
                                ?: "-",
                            axis = orderDetails.powerDetails?.right?.axis?.checkNullorEmpty()
                                ?: "-",
                            addition = orderDetails.powerDetails?.right?.addition?.checkNullorEmpty()
                                ?: "-",
                            colorScheme = colorScheme
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Left Eye
                        EyeDetailCard(
                            eyeTitle = "Left Eye",
                            spherical = orderDetails.powerDetails?.left?.spherical?.checkNullorEmpty()
                                ?: "-",
                            cylindrical = orderDetails.powerDetails?.left?.cylindrical?.checkNullorEmpty()
                                ?: "-",
                            axis = orderDetails.powerDetails?.left?.axis?.checkNullorEmpty() ?: "-",
                            addition = orderDetails.powerDetails?.left?.addition?.checkNullorEmpty()
                                ?: "-",
                            colorScheme = colorScheme
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier.width(130.dp)
        )

        Text(
            text = value,
            fontSize = 14.sp,
            color = textColor.copy(alpha = 0.85f)
        )
    }
}

@Composable
fun EyeDetailCard(
    eyeTitle: String,
    spherical: String,
    cylindrical: String,
    axis: String,
    addition: String,
    colorScheme: ColorSchemeModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (eyeTitle == "Right Eye")
                        Color(0xFF1976D2).copy(alpha = 0.1f)
                    else
                        Color(0xFF7B1FA2).copy(alpha = 0.1f)
                )
                .padding(16.dp)
        ) {
            Text(
                text = eyeTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (eyeTitle == "Right Eye") Color(0xFF1976D2) else Color(0xFF7B1FA2),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    PowerDetailItem(
                        label = "Spherical",
                        value = spherical,
                        textColor = colorScheme.compColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PowerDetailItem(
                        label = "Axis",
                        value = axis,
                        textColor = colorScheme.compColor
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    PowerDetailItem(
                        label = "Cylindrical",
                        value = cylindrical,
                        textColor = colorScheme.compColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PowerDetailItem(
                        label = "Addition",
                        value = addition,
                        textColor = colorScheme.compColor
                    )
                }
            }
        }
    }
}

@Composable
fun PowerDetailItem(
    label: String,
    value: String,
    textColor: Color
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            color = textColor.copy(alpha = 0.7f)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}