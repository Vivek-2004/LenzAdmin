package com.fitting.lenz.screens

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.fitting.lenz.CustomTimerFAB
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.components.GroupOrderItemHolder
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
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
        "Internal Tracking",
        "Delivery Accepted",
        "Out For Delivery",
        "Order Completed"
    )
    val context = LocalContext.current
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()

    var isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
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
    } else if (responseCode == 400 || responseCode == 404 || responseCode == 500) {
        Toast.makeText(context, "Pickup Failed", Toast.LENGTH_SHORT).show()
        responseCode = -2
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) return@LaunchedEffect
        delay(3000) // 3 seconds delay
        isLoading = false
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        lenzViewModel.getGroupOrders()
        delay(2000L)
        isRefreshing = false
    }

    LaunchedEffect(callForPickup) {
        if (!callForPickup) return@LaunchedEffect
        try {
            lenzViewModel.callForPickup(
                requestBody = selectedIds,
                delAmount = amount
            )
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

    if (showDialog) {
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
                        color = Color("#38b000".toColorInt())
                    )
                    Text(
                        text = "Pickup Charge Paid : ₹${totalDeliveryChargeCollected * 40 / 100}",
                        fontSize = 13.5.sp,
                        color = Color.Red
                    )
                    Text(
                        text = "Remaining Margin : ₹${totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40 / 100)}",
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
                    val temp = tempAmount.toDoubleOrNull() ?: 0.0
                    if (temp <= 0.0 || temp > (totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40 / 100))) {
                        errorMessage =
                            "Enter Valid Delivery Charge between 1 and ${totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40 / 100)}"
                        tempAmount = ""
                    } else {
                        amount = temp
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
                    forceReset = !forceReset
                }) {
                    Text(text = "Cancel", fontSize = 16.sp)
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
            .background(colorScheme.bgColor.copy(0.1f))
    ) {
        if (lenzViewModel.groupOrders.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.bgColor)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        strokeWidth = 11.dp,
                        color = Color.DarkGray
                    )
                } else {
                    Text("No Orders Found")
                }
            }
        } else {
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

                if (orderGroups.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No $statusSelectedItem",
                            fontSize = 18.sp
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    lenzViewModel.getGroupOrders()
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Click to Refresh")
                        }
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
                if (lenzViewModel.groupOrders.filter {
                        it.trackingStatus == "Delivery Accepted" || it.trackingStatus == "Order Picked Up"
                    }.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            if (lenzViewModel.groupOrders.filter { it.trackingStatus == "Order Picked Up" }
                                    .isNotEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Filter \"Order Picked Up\" from Menu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            statusSelectedItem = "Delivery Accepted"
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(horizontal = 80.dp, vertical = 50.dp),
                    ) {
                        Icon(
                            modifier = Modifier.size(45.dp),
                            painter = painterResource(R.drawable.delivery),
                            contentDescription = null
                        )
                    }
                }
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
                        countdownSeconds = 2,
                        resetTrigger = forceReset,
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
                                    Toast.makeText(
                                        context,
                                        "Filter Status: $statusSelectedItem",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}