package com.fitting.lenz.screens.details_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.formDate
import com.fitting.lenz.models.ColorSchemeModel
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
    var showDistanceDialog by remember { mutableStateOf(false) }
    var showCreditDialog by remember { mutableStateOf(false) }
    var updateDistance by remember { mutableStateOf(false) }
    var updateCredit by remember { mutableStateOf(false) }

    val shop by remember { mutableStateOf(lenzViewModel.shopsList.find { shopId == it._id }!! ) }
    var creditBalance by remember { mutableDoubleStateOf(shop.creditBalance) }

    var distance by remember { mutableStateOf(shop.distance.toString()) }
    var tempDistance by remember { mutableStateOf("") }

    var amountPaid by remember { mutableStateOf("0") }
    var tempAmountPaid by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(updateDistance) {
        if(!updateDistance) return@LaunchedEffect
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
        if(!updateCredit) return@LaunchedEffect
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

    if(showDistanceDialog) {
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
                        text = "Enter New distance:",
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempDistance,
                        onValueChange = { tempDistance = it },
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
                    if(tempDistance.replace(Regex("[\\s,]+"), "").isEmpty() || round(tempDistance.toDouble()).toInt() < 1) {
                        errorMessage = "Enter Valid Distance Greater Than 1"
                        tempDistance = ""
                    } else {
                        distance = round(tempDistance.toDouble()).toInt().toString()
                        tempDistance = ""
                        updateDistance = true
                        showDistanceDialog = false
                        errorMessage = ""
                        Toast.makeText(context, "Distance Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Update", fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    errorMessage = ""
                    tempDistance = ""
                    showDistanceDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            }
        )
    }

    if(showCreditDialog) {
        AlertDialog(
            onDismissRequest = {
                showCreditDialog = false
                errorMessage = ""
                tempAmountPaid = ""
            },
            title = { Text(text = "Update Credit Balance") },
            text = {
                Column {
                    Text(
                        text = "Enter Amount Paid:",
                        fontSize = 15.sp
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
                    if( tempAmountPaid.replace(Regex("[\\s,]+"), "").isEmpty() ||
                        tempAmountPaid.toDouble().toInt() <= 0.0 ||
                        tempAmountPaid.toDouble() > creditBalance
                    )  {
                        errorMessage = "Enter a Valid Amount"
                        tempAmountPaid = ""
                    } else {
                        amountPaid = tempAmountPaid
                        creditBalance -= amountPaid.toDouble()
                        tempAmountPaid = ""
                        updateCredit = true
                        showCreditDialog = false
                        errorMessage = ""
                        Toast.makeText(context, "Credit Balance Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Update", fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempAmountPaid = ""
                    errorMessage = ""
                    showCreditDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
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
            modifier = Modifier.fillMaxWidth().padding(end = 30.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Member Since ${shop.createdAt.formDate()}",
                color = colorScheme.compColor,
                textAlign = TextAlign.End,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
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
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Phone: ")
                    }
                    append(shop.phone)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Alternate Phone: ")
                    }
                    append(shop.alternatePhone)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
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
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Distance: ")
                        }
                        append("$distance km")
                    },
                    fontSize = 16.sp,
                    color = colorScheme.compColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.size(17.dp).padding(top = 2.dp),
                    onClick = {
                        showDistanceDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Distance",
                        tint = Color.Cyan
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Credit: ")
                        }
                        append("₹$creditBalance")
                    },
                    fontSize = 16.sp,
                    color = Color.Red.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.size(17.dp).padding(top = 2.dp),
                    onClick = {
                        showCreditDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Credit Amount",
                        tint = Color.Green
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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