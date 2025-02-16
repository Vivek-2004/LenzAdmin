package com.fitting.lenz.screens

import android.graphics.Color.parseColor
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.CustomTimerFAB
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.components.GroupOrderItemHolder
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Orders(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController
) {
    val orderStates = listOf(
        "All Orders",
        "Order Placed For Pickup",
        "Pickup Accepted",
        "Order Picked Up",
        "Order Received By Admin",
        "Work Completed",
        "Out For Delivery",
        "Order Completed"
    )
    val context = LocalContext.current

    var filterExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var forceReset by remember { mutableStateOf(false) }
    var callForPickup by remember { mutableStateOf(false) }

    var statusSelectedItem by remember { mutableStateOf(orderStates[4]) }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    val inSelectionMode by remember { derivedStateOf { selectedIds.isNotEmpty() } }

    var tempAmount by remember { mutableStateOf("") }
    var amount by remember { mutableDoubleStateOf(0.0) }
    var errorMessage by remember { mutableStateOf("") }
    var responseCode by lenzViewModel::pickupResponseCode

    val orderGroups = lenzViewModel.groupOrders.filter {
        statusSelectedItem == "All Orders" || it.trackingStatus == statusSelectedItem
    }

    var totalDeliveryChargeCollected = 0
    selectedIds.forEach { id ->
        totalDeliveryChargeCollected += orderGroups.find { id == it.id }?.deliveryCharge ?: 0
    }

    if (responseCode == 200) {
        Toast.makeText(context, "Pickup Initiated with Charge  ₹$amount", Toast.LENGTH_SHORT).show()
        responseCode = -1
    } else if(responseCode == 400 || responseCode == 404 || responseCode == 500)  {
        Toast.makeText(context, "Pickup Failed", Toast.LENGTH_SHORT).show()
        responseCode = -2
    }

    LaunchedEffect(callForPickup) {
        if(!callForPickup) return@LaunchedEffect
        try {
            lenzViewModel.callForPickup(selectedIds)
        } finally {
            selectedIds = emptySet()
            forceReset = !forceReset
            delay(1800)
            lenzViewModel.getGroupOrders()
            callForPickup = false
        }
    }

    LaunchedEffect(statusSelectedItem, orderGroups) {
        selectedIds = emptySet()
    }

    BackHandler(enabled = inSelectionMode) {
        selectedIds = emptySet()
        forceReset = !forceReset
    }

    if(showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                errorMessage = ""
                tempAmount = ""
                forceReset = !forceReset
            },
            title = { Text(text = "Set Delivery Charge") },
            text = {
                Column {
                    Text(
                        text = "Total Charge Collected : ₹$totalDeliveryChargeCollected",
                        fontSize = 13.5.sp,
                        color = Color(parseColor("#38b000"))
                    )
                    Text(
                        text = "Pickup Charge Paid : ₹${totalDeliveryChargeCollected * 40/100}",
                        fontSize = 13.5.sp,
                        color = Color.Red
                    )
                    Text(
                        text = "Remaining Margin : ₹${totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40/100)}",
                        fontSize = 13.5.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Set Order Delivery Amount:",
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempAmount,
                        onValueChange = { tempAmount = it },
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
                    if( tempAmount.replace(Regex("[\\s,]+"), "").isEmpty() ||
                        tempAmount.toDouble() <= 0.0 ||
                        tempAmount.toDouble() > (totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40/100))
                    ) {
                        errorMessage = "Delivery Charge Must be between 1 and ${totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40/100)}"
                        tempAmount = ""
                    } else {
                        amount = tempAmount.toDouble()
                        tempAmount = ""
                        callForPickup = true
                        showDialog = false
                        errorMessage = ""
                    }
                }) {
                    Text(text = "Confirm", fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    errorMessage = ""
                    tempAmount = ""
                    showDialog = false
                }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 13.dp, end = 13.dp, bottom = 6.dp, top = 4.dp),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = colorScheme.bgColor),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = statusSelectedItem,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        if(orderGroups.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No $statusSelectedItem",
                    fontSize = 18.sp
                )
            }
        } else {
            GroupOrderItemHolder(
                colorScheme = colorScheme,
                lenzViewModel = lenzViewModel,
                navController = navController,
                orderGroups = orderGroups,
                onSelectedIdsChange = { ids -> selectedIds = ids },
                inSelectionMode = inSelectionMode,
                forceReset = forceReset
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (selectedIds.isEmpty()) {
            FloatingActionButton(
                onClick = {
                    statusSelectedItem = "Work Completed"
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 18.dp, vertical = 85.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null
                )
            }
        } else {
            CustomTimerFAB(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(vertical = 70.dp),
                countdownSeconds = 3,
                onCountdownEnd = {
                    showDialog = true
                }
            )
        }

        FloatingActionButton(
            onClick = {
                filterExpanded = !filterExpanded
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(18.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.filter),
                contentDescription = "Tracking Status Filter"
            )

            DropdownMenu(
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
                    .background(Color.LightGray),
                expanded = filterExpanded,
                onDismissRequest = { filterExpanded = false },
            ) {
                orderStates.forEach { orderStateItem ->
                    DropdownMenuItem(
                        modifier = Modifier.background(
                            if (orderStateItem == statusSelectedItem) Color.Gray
                            else Color.Transparent
                        ),
                        text = {
                            Text(
                                text = orderStateItem,
                                color = colorScheme.compColor
                            )
                        },
                        onClick = {
                            filterExpanded = false
                            statusSelectedItem = orderStateItem
                            Toast.makeText(context, "Filter Status: $statusSelectedItem", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}