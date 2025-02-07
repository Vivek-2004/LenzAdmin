package com.fitting.lenz.screens

import android.os.Build
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.components.GroupOrderItemHolder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Orders(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel,
    navController: NavController
) {
    val context = LocalContext.current
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
    var orderSelectedItem by remember { mutableStateOf(orderStates[3]) }
    var expanded by remember { mutableStateOf(false) }

    val orderGroups = lenzViewModel.groupOrders.filter {
        orderSelectedItem == "All Orders" || it.trackingStatus == orderSelectedItem
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
                    text = orderSelectedItem,
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
            ){
                Text(
                    text = "No $orderSelectedItem",
                    fontSize = 18.sp
                )
            }
        } else {
            GroupOrderItemHolder(
                colorScheme = colorScheme,
                lenzViewModel = lenzViewModel,
                navController = navController,
                orderGroups = orderGroups
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = {
//                expanded = !expanded
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 18.dp, vertical = 90.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = null
            )
        }
        FloatingActionButton(
            onClick = {
                expanded = !expanded
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
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                orderStates.forEach { orderStateItem ->
                    DropdownMenuItem(
                        modifier = Modifier.background(
                            if (orderStateItem == orderSelectedItem) Color.Gray
                            else Color.Transparent
                        ),
                        text = {
                            Text(
                                text = orderStateItem,
                                color = colorScheme.compColor
                            )
                        },
                        onClick = {
                            expanded = false
                            orderSelectedItem = orderStateItem
                            Toast.makeText(context, "Filter Status: $orderSelectedItem", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}