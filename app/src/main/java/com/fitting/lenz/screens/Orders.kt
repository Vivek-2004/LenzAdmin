package com.fitting.lenz.screens

import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Pin
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
import com.fitting.lenz.navigation.NavigationDestination
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
    LaunchedEffect(Unit) {
        lenzViewModel.getGroupOrders()
    }

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
        delay(4000)
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
            title = {
                Text(
                    text = "Set Delivery Charge",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        ) {
                            Text(
                                text = "Total Charge Collected: ₹$totalDeliveryChargeCollected",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color("#38b000".toColorInt())
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Pickup Charge Paid: ₹${totalDeliveryChargeCollected * 40 / 100}",
                                fontSize = 13.sp,
                                color = Color.Red
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Remaining Margin: ₹${totalDeliveryChargeCollected - (totalDeliveryChargeCollected * 40 / 100)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Text(
                        text = "Set Order Delivery Amount:",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    OutlinedTextField(
                        value = tempAmount,
                        onValueChange = { tempAmount = it },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter amount") },
                        shape = RoundedCornerShape(8.dp)
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            fontSize = 12.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
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
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Confirm",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.compColor
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        errorMessage = ""
                        tempAmount = ""
                        showDialog = false
                        forceReset = !forceReset
                    }
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
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
            .background(Color.White)
    ) {
        if (lenzViewModel.groupOrders.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.bgColor.copy(alpha = 0.05f))
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        strokeWidth = 8.dp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Loading Orders...",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "No Orders Found",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 16.dp),
                        tint = Color.Gray
                    )
                    Text(
                        "No Orders Found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Pull Down to Refresh",
                        fontSize = 13.sp,
                        color = colorScheme.compColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.bgColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = statusSelectedItem,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        if (inSelectionMode) {
                            Text(
                                text = "${selectedIds.size} Selected",
                                color = colorScheme.compColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                if (orderGroups.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                            .verticalScroll(scrollState)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE9ECEF),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "No Orders Yet!",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Pull Down to Refresh",
                            color = Color.DarkGray,
                            fontSize = 12.sp
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
                if (lenzViewModel.groupOrders.any {
                        it.trackingStatus == "Delivery Accepted" || it.trackingStatus == "Order Picked Up"
                    }
                ) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(NavigationDestination.ActiveOrders.name)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(horizontal = 80.dp, vertical = 50.dp),
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = Icons.Default.Pin,
                            contentDescription = null,
                            tint = Color.White
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
                        containerColor = Color(0xFF2196F3),
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call for Pickup",
                            modifier = Modifier.size(24.dp)
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
                        .padding(18.dp),
                    containerColor = colorScheme.compColor,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.filter),
                        contentDescription = "Tracking Status Filter",
                        modifier = Modifier.size(24.dp)
                    )

                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize(Alignment.TopStart)
                            .background(Color.White),
                        expanded = filterExpanded,
                        onDismissRequest = { filterExpanded = false },
                    ) {
                        orderStates.forEach { orderStateItem ->
                            DropdownMenuItem(
                                modifier = Modifier.background(
                                    if (orderStateItem == statusSelectedItem)
                                        Color.LightGray
                                    else
                                        Color.Transparent
                                ),
                                text = {
                                    Text(
                                        text = orderStateItem,
                                        color = if (orderStateItem == statusSelectedItem)
                                            colorScheme.compColor
                                        else
                                            Color.DarkGray,
                                        fontWeight = if (orderStateItem == statusSelectedItem)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    filterExpanded = false
                                    statusSelectedItem = orderStateItem
                                    Toast.makeText(
                                        context,
                                        "Filter Result: $statusSelectedItem",
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