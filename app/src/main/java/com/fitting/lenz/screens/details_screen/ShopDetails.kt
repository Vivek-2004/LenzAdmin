package com.fitting.lenz.screens.details_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.roundToTwoDecimalPlaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.round

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopDetails(
    lenzViewModel: LenzViewModel,
    colorScheme: ColorSchemeModel,
    shopId: String
) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            lenzViewModel.getShopsList()
        }
    }
    val context = LocalContext.current
    var updateDistance by remember { mutableStateOf(false) }
    var updateCredit by remember { mutableStateOf(false) }

    var showDistanceDialog by remember { mutableStateOf(false) }
    var showCreditPlusDialog by remember { mutableStateOf(false) }
    var showCreditMinusDialog by remember { mutableStateOf(false) }

    val shop by remember { mutableStateOf(lenzViewModel.shopsList.find { shopId == it._id }!!) }
    var creditBalance by remember { mutableDoubleStateOf(shop.creditBalance.roundToTwoDecimalPlaces()) }

    var distance by remember { mutableStateOf(shop.distance.toString()) }
    var tempDistance by remember { mutableStateOf("") }

    var amountPaid by remember { mutableStateOf("0") }
    var tempAmountPaid by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(updateDistance) {
        if (!updateDistance) return@LaunchedEffect
        try {
            withContext(Dispatchers.IO) {
                lenzViewModel.editShopDistance(
                    shopId = shop.userId,
                    newDistance = round(distance.toDouble()).toInt()
                )
            }
        } finally {
            lenzViewModel.getShopsList()
            updateDistance = false
        }
    }

    LaunchedEffect(updateCredit) {
        if (!updateCredit) return@LaunchedEffect
        try {
            withContext(Dispatchers.IO) {
                lenzViewModel.editShopCredit(
                    shopId = shop.userId,
                    newBalance = creditBalance
                )
            }
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
                        text = "Current Distance: $distance km",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter New Distance",
                        fontSize = 19.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempDistance,
                        onValueChange = { tempDistance = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
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
                    if (tempDistance.toInt() < 1 || tempDistance.toInt() > 10) {
                        errorMessage = "Enter Distance Between 1 and 10 kms"
                        tempDistance = ""
                    } else {
                        distance = tempDistance
                        tempDistance = ""
                        updateDistance = true
                        showDistanceDialog = false
                        errorMessage = ""
                        Toast.makeText(context, "Distance Updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                    Text(text = "Update", fontSize = 14.5.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    errorMessage = ""
                    tempDistance = ""
                    showDistanceDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.5.sp)
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
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter Amount Received",
                        fontSize = 19.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempAmountPaid,
                        onValueChange = { tempAmountPaid = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
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
                    Text(text = "Update", fontSize = 14.5.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempAmountPaid = ""
                    errorMessage = ""
                    showCreditMinusDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.5.sp)
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
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Enter Credit Amount",
                        fontSize = 19.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempAmountPaid,
                        onValueChange = { tempAmountPaid = it },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
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
                    Text(text = "Update", fontSize = 14.5.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempAmountPaid = ""
                    errorMessage = ""
                    showCreditPlusDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 14.5.sp)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.bgColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = shop.shopName,
                color = colorScheme.compColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Member Since ${shop.createdAt.formDate()}",
                color = colorScheme.compColor,
                textAlign = TextAlign.End,
                fontSize = 12.sp
            )
        }
        (15..185 step 13).forEach { padding ->
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = padding.dp),
                thickness = 4.dp,
                color = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Dealer: ")
                    }
                    append(shop.name)
                },
                fontSize = 20.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("User ID: ")
                    }
                    append(shop.userId.toString())
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Primary Phone: ")
                    }
                    append("+91-${shop.phone.takeLast(10)}")
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            if (shop.alternatePhone.replace("+91", "").isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Alternate Phone: ")
                        }
                        append("+91-${shop.alternatePhone.takeLast(10)}")
                    },
                    fontSize = 16.sp,
                    color = colorScheme.compColor
                )
            }
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mail: ")
                    }
                    append(shop.email)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .weight(1.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Distance: ")
                            }
                            append("$distance km")
                        },
                        fontSize = 20.sp,
                        color = colorScheme.compColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                        .weight(0.3f)
                        .clickable {
                            showDistanceDialog = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Distance"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .weight(1.6f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Credit: ")
                            }
                            withStyle(style = SpanStyle(fontFamily = FontFamily.SansSerif)) {
                                append("₹")
                            }
                            append(creditBalance.roundToTwoDecimalPlaces().toString())
                        },
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                        .weight(0.35f)
                        .clickable {
                            if (creditBalance == 0.0) {
                                Toast.makeText(context, "No Pending Payments", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                showCreditMinusDialog = true
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "Minus Credit Amount",
                        tint = Color.Red
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                        .weight(0.35f)
                        .clickable {
                            showCreditPlusDialog = true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.plus),
                        contentDescription = "Plus Credit Amount",
                        tint = Color.Green
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Address Line 1: ")
                    }
                    append(shop.address.line1)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Address Line 2: ")
                    }
                    append(shop.address.line2)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Landmark: ")
                    }
                    append(shop.address.landmark)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("City: ")
                    }
                    append(shop.address.city)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "${shop.address.state}, ${shop.address.pinCode}",
                fontSize = 16.sp,
                color = colorScheme.compColor,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}