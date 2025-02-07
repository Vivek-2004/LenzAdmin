package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fitting.lenz.models.ColorSchemeModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.checkNullorEmpty
import com.fitting.lenz.formatPaymentStatus
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
            if(singleOrder.id == orderId) {
                orderDetails = singleOrder
            }
        }
    }
    val scrollState = rememberScrollState()
    val paymentStatus = formatPaymentStatus(orderDetails.paymentStatus)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "LenZ Spectacle Store",
                    fontSize = 20.sp,
                    color = Color.Gray.copy(alpha = 0.7f)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Order ")
                    }
                    append("#$orderId")
                },
                fontSize = 15.sp,
                color = colorScheme.compColor
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Shop Name: ")
                    }
                    append(lenzViewModel.shopsList.filter { orderDetails.userId == it._id }[0].shopName)
                },
                fontSize = 18.sp,
                color = colorScheme.compColor
                )
                Text(
                    text = "$paymentStatus Order : Rs.${orderDetails.totalAmount}/-",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = (
                            if (paymentStatus == "Paid") Color.Green
                            else Color.Red
                            ).copy(alpha = 0.7f)
                )
                Text(
                    text = "[ Delivery Charges are always Paid ]",
                    fontSize = 11.sp,
                    fontStyle = FontStyle.Italic,
                    color = colorScheme.compColor
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
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Order Type: ")
                    }
                    append(orderDetails.shiftingOrFitting)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Frame Type: ")
                    }
                    append(orderDetails.frameOptions.type)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )

            if (orderDetails.shiftingOrFitting == "Fitting") {

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Glass Type: ")
                        }
                        append(orderDetails.glassType)
                    },
                    fontSize = 16.sp,
                    color = colorScheme.compColor
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Lens Type: ")
                        }
                        append(orderDetails.lensDetails)
                    },
                    fontSize = 16.sp,
                    color = colorScheme.compColor
                )

                if(!orderDetails.materialDetails.isNullOrEmpty()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Material Details: ")
                            }
                            append(orderDetails.materialDetails)
                        },
                        fontSize = 16.sp,
                        color = colorScheme.compColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Power Details :-",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.compColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding( start = 12.dp, top = 8.dp)
                ) {
                    Text(
                        text = "Right Eye",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.compColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //Right Eye
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                text = "Spherical: ${orderDetails.powerDetails?.right?.spherical?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                            Text(
                                text = "Axis: ${orderDetails.powerDetails?.right?.axis?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                text = "Cylindrical: ${orderDetails.powerDetails?.right?.cylindrical?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                            Text(
                                text = "Addition: ${orderDetails.powerDetails?.right?.addition?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider( modifier = Modifier.width(300.dp) )
                    Spacer(modifier = Modifier.height(12.dp))

                    //Left Eye
                    Text(
                        text = "Left Eye",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.compColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                text = "Spherical: ${orderDetails.powerDetails?.left?.spherical?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                            Text(
                                text = "Axis: ${orderDetails.powerDetails?.left?.axis?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().weight(1f)
                        ) {
                            Text(
                                text = "Cylindrical: ${orderDetails.powerDetails?.left?.cylindrical?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                            Text(
                                text = "Addition: ${orderDetails.powerDetails?.left?.addition?.checkNullorEmpty()}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.compColor
                            )
                        }
                    }
                }
            }
        }
    }
}