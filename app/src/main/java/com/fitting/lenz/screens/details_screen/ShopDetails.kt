package com.fitting.lenz.screens.details_screen

import android.os.Build
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.formDate
import com.fitting.lenz.models.Address
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.roundToTwoDecimalPlaces
import kotlinx.coroutines.delay
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopDetails(
    shopId: String,
    lenzViewModel: LenzViewModel,
    colorScheme: ColorSchemeModel
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val shop = lenzViewModel.shopsList.find { it._id == shopId }!!

    var isRefreshing by remember { mutableStateOf(false) }
    var updateDistance by remember { mutableStateOf(false) }
    var updateCredit by remember { mutableStateOf(false) }
    var showDistanceDialog by remember { mutableStateOf(false) }
    var showCreditPlusDialog by remember { mutableStateOf(false) }
    var showCreditMinusDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var tempDistance by remember { mutableStateOf("") }

    var creditBalance by remember { mutableDoubleStateOf(shop.creditBalance.roundToTwoDecimalPlaces()) }
    var amountPaid by remember { mutableStateOf("0") }
    var tempAmountPaid by remember { mutableStateOf("") }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        lenzViewModel.getShopsList()
        delay(800)
        isRefreshing = false
    }

    LaunchedEffect(updateDistance) {
        if (!updateDistance) return@LaunchedEffect
        try {
            lenzViewModel.editShopDistance(
                shopId = shop.userId,
                newDistance = round(tempDistance.toDouble()).toInt()
            )
            delay(700)
        } finally {
            lenzViewModel.getShopsList()
            tempDistance = ""
            updateDistance = false
        }
    }

    LaunchedEffect(updateCredit) {
        if (!updateCredit) return@LaunchedEffect
        try {
            lenzViewModel.editShopCredit(
                shopId = shop.userId,
                newBalance = creditBalance
            )
            delay(700)
        } finally {
            lenzViewModel.getShopsList()
            updateCredit = false
        }
    }

    if (showDistanceDialog) {
        AlertDialog(
            onDismissRequest = {
                showDistanceDialog = false
                errorMessage = ""
                tempDistance = ""
            },
            title = { Text(text = "Edit Shop Distance") },
            text = {
                Column {
                    Text(
                        text = "Current Distance: ${shop.distance} km",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Enter New Distance",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempDistance,
                        onValueChange = { tempDistance = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = errorMessage,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = tempDistance.isNotEmpty(),
                    onClick = {
                        if (tempDistance.toIntOrNull() == null || tempDistance.toInt() < 1 || tempDistance.toInt() > 10) {
                            errorMessage = "Enter Distance Between 1 and 10 kms"
                            tempDistance = ""
                        } else {
                            updateDistance = true
                            showDistanceDialog = false
                            errorMessage = ""
                            Toast.makeText(
                                context,
                                "Distance Updated Successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }) {
                    Text(text = "Update", fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    errorMessage = ""
                    tempDistance = ""
                    showDistanceDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.sp)
                }
            }
        )
    }

    if (showCreditMinusDialog && creditBalance != 0.0) {
        AlertDialog(
            onDismissRequest = {
                showCreditMinusDialog = false
                errorMessage = ""
                tempAmountPaid = ""
            },
            title = { Text(text = "Deduct Credit Balance") },
            text = {
                Column {
                    Text(
                        text = "Current Credit Amount: ₹$creditBalance",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Enter Amount Received",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempAmountPaid,
                        onValueChange = { tempAmountPaid = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = errorMessage,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val temp = tempAmountPaid.toDoubleOrNull() ?: 0.0
                    if (temp <= 0.0 || temp > creditBalance) {
                        errorMessage = "Enter a Valid Amount"
                        tempAmountPaid = ""
                    } else {
                        amountPaid = temp.toString()
                        creditBalance -= amountPaid.toDouble().roundToTwoDecimalPlaces()
                        tempAmountPaid = ""
                        updateCredit = true
                        showCreditMinusDialog = false
                        errorMessage = ""
                        Toast.makeText(
                            context,
                            "Credit Balance Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = "Update", fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempAmountPaid = ""
                    errorMessage = ""
                    showCreditMinusDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.sp)
                }
            }
        )
    }

    if (showCreditPlusDialog) {
        AlertDialog(
            onDismissRequest = {
                showCreditPlusDialog = false
                errorMessage = ""
                tempAmountPaid = ""
            },
            title = { Text(text = "Add Credit Balance") },
            text = {
                Column {
                    Text(
                        text = "Current Credit Amount: ₹$creditBalance",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Enter Credit Amount",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempAmountPaid,
                        onValueChange = { tempAmountPaid = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = errorMessage,
                        fontSize = 12.sp,
                        color = Color.Red
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val temp = tempAmountPaid.toDoubleOrNull() ?: 0.0
                    if (temp <= 0.0) {
                        errorMessage = "Enter a Valid Amount"
                        tempAmountPaid = ""
                    } else {
                        amountPaid = temp.toString()
                        creditBalance += amountPaid.toDouble().roundToTwoDecimalPlaces()
                        tempAmountPaid = ""
                        updateCredit = true
                        showCreditPlusDialog = false
                        errorMessage = ""
                        Toast.makeText(
                            context,
                            "Credit Balance Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = "Update", fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempAmountPaid = ""
                    errorMessage = ""
                    showCreditPlusDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.sp)
                }
            }
        )
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            ShopHeader(
                shopName = shop.shopName,
                creationDate = shop.createdAt.formDate(),
                textColor = colorScheme.compColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Information Card
            ContactInfoCard(
                shop.name,
                shop.userId.toString(),
                shop.phone,
                shop.alternatePhone,
                shop.email,
                colorScheme.compColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Distance Information Card with Edit Button
            DistanceCard(
                distance = shop.distance,
                colorScheme = colorScheme,
                onEditClick = { showDistanceDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Credit Balance Card with Add/Subtract buttons
            CreditBalanceCard(
                creditBalance = creditBalance,
                colorScheme = colorScheme,
                onMinusClick = {
                    if (creditBalance == 0.0) {
                        Toast.makeText(context, "No Pending Payments", Toast.LENGTH_SHORT).show()
                    } else {
                        showCreditMinusDialog = true
                    }
                },
                onPlusClick = { showCreditPlusDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            AddressCard(shop.address, colorScheme.compColor)
        }
    }
}

@Composable
fun ShopHeader(
    shopName: String,
    creationDate: String,
    textColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = shopName,
            color = textColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 33.sp,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Member Since $creationDate",
            color = textColor.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 32.dp),
            thickness = 2.dp,
            color = textColor.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun ContactInfoCard(
    dealerName: String,
    userId: String,
    phone: String,
    alternatePhone: String,
    email: String,
    textColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Person Info Row
            InfoRow(
                icon = Icons.Rounded.Person,
                iconTint = textColor,
                title = "Dealer:",
                value = dealerName,
                textColor = textColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            // User ID Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(textColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "#",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "User ID:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = userId,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = textColor.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(16.dp))

            // Primary Phone Row
            InfoRow(
                icon = Icons.Rounded.Phone,
                iconTint = textColor,
                title = "Primary:",
                value = phone.takeLast(10),
                textColor = textColor
            )

            // Alternate Phone Row (if exists)
            if (alternatePhone.replace("+91", "").isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(
                    icon = Icons.Rounded.Phone,
                    iconTint = textColor.copy(alpha = 0.6f),
                    title = "Alternate :",
                    value = alternatePhone.takeLast(10),
                    textColor = textColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Email Row
            InfoRow(
                icon = Icons.Rounded.Email,
                iconTint = textColor,
                title = "",
                value = email,
                textColor = textColor
            )
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    value: String,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(iconTint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = textColor.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        if (title == "Dealer:") {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        } else {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun DistanceCard(
    distance: Int,
    colorScheme: ColorSchemeModel,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(colorScheme.compColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocationOn,
                    contentDescription = "Distance",
                    tint = colorScheme.compColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Distance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.compColor.copy(alpha = 0.7f)
                )

                Text(
                    text = "$distance km",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.compColor
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable(onClick = onEditClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit Distance",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun CreditBalanceCard(
    creditBalance: Double,
    colorScheme: ColorSchemeModel,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colorScheme.compColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CreditCard,
                        contentDescription = "Credit Balance",
                        tint = colorScheme.compColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Credit Balance",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.compColor.copy(alpha = 0.7f)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontFamily = FontFamily.SansSerif)) {
                                append("₹")
                            }
                            append(creditBalance.roundToTwoDecimalPlaces().toString())
                        },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (creditBalance > 0) Color(0xFF2E7D32) else colorScheme.compColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ActionButton(
                    color = Color(0xFFD32F2F),
                    onClick = onMinusClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                ActionButton(
                    color = Color(0xFF2E7D32),
                    onClick = onPlusClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(
    color: Color,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun AddressCard(address: Address, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(textColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "Address",
                        tint = textColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Shop Address",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddressField("Address Line 1", address.line1, textColor)
            Spacer(modifier = Modifier.height(8.dp))
            AddressField("Address Line 2", address.line2, textColor)
            Spacer(modifier = Modifier.height(8.dp))
            AddressField("Landmark", address.landmark, textColor)
            Spacer(modifier = Modifier.height(8.dp))
            AddressField("City", address.city, textColor)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${address.state}, ${address.pinCode}",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun AddressField(label: String, value: String, textColor: Color) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}