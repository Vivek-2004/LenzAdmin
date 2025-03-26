package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.automirrored.rounded.DirectionsBike
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.RiderDetails
import com.fitting.lenz.screens.StatusIndicator
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val LightGray = Color(0xFFF8F9FA)
private val DarkTextColor = Color(0xFF2C3E50)
private val SecondaryTextColor = Color(0xFF7F8C8D)
private val BluePrimary = Color(0xFF3498DB)
private val BlueDark = Color(0xFF2980B9)
private val BorderColor = Color(0xFFF3F4F6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiderDetails(
    lenzViewModel: LenzViewModel,
    riderId: String
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()
    val rider = lenzViewModel.ridersList.find { it.riderId == riderId }!!

    var isRefreshing by remember { mutableStateOf(false) }
    val backgroundGradient = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color.DarkGray,
                Color.Gray,
                Color.LightGray
            )
        )
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        lenzViewModel.getAllRiders()
        delay(1000)
        isRefreshing = false
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        },
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RiderProfileCard(rider = rider)
            RiderStatisticsCard(rider = rider)
            RiderContactInfoCard(rider = rider)
            RiderAdditionalInfoCard(rider = rider)
        }
    }
}

@Composable
fun RiderProfileCard(rider: RiderDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(16.dp)), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(BluePrimary, BlueDark)
                        )
                    )
                    .border(3.dp, BorderColor, CircleShape), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Rider",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = rider.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            Text(
                text = "#${rider.riderId}", fontSize = 16.sp, color = SecondaryTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusIndicator(rider = rider)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGray)
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.DirectionsBike,
                    contentDescription = "Vehicle",
                    tint = BluePrimary,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = rider.vehicleNumber,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkTextColor
                )
            }
        }
    }
}

@Composable
fun RiderStatisticsCard(rider: RiderDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(16.dp)), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Earnings & Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "Total Earnings",
                    value = "₹${rider.totalEarnings}",
                    valueColor = Color(0xFF27AE60),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                StatCard(
                    title = "Total Orders",
                    value = rider.totalOrders.toString(),
                    valueColor = BluePrimary,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(
                    title = "Today Earnings",
                    value = "₹${rider.dailyEarnings}",
                    valueColor = Color(0xFF27AE60),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                StatCard(
                    title = "Today Orders",
                    value = rider.dailyOrders.toString(),
                    valueColor = BluePrimary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String, value: String, valueColor: Color, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title, fontSize = 12.sp, color = SecondaryTextColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valueColor
            )
        }
    }
}

@Composable
fun RiderContactInfoCard(rider: RiderDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(16.dp)), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Contact Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContactItem(
                icon = Icons.Filled.Phone, label = "Phone", value = rider.phone
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContactItem(
                icon = Icons.Filled.Email, label = "Email", value = rider.email
            )
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector, label: String, value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = BluePrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = label, fontSize = 13.sp, color = SecondaryTextColor
            )

            Text(
                text = value, fontSize = 15.sp, color = DarkTextColor
            )
        }
    }
}

@Composable
fun RiderAdditionalInfoCard(rider: RiderDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(16.dp)), colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Additional Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
            val formattedDate = remember(rider.createdAt) {
                try {
                    val date =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(
                            rider.createdAt
                        )
                    dateFormat.format(date ?: Date())
                } catch (e: Exception) {
                    "Unknown date"
                }
            }

            ContactItem(
                icon = Icons.Filled.CalendarMonth, label = "Registered On", value = formattedDate
            )

            Spacer(modifier = Modifier.height(16.dp))

            val lastActiveDate = remember(rider.updatedAt) {
                try {
                    val date =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(
                            rider.updatedAt
                        )
                    dateFormat.format(date ?: Date())
                } catch (e: Exception) {
                    "Unknown date"
                }
            }

            ContactItem(
                icon = Icons.AutoMirrored.Filled.DirectionsWalk,
                label = "Last Active",
                value = lastActiveDate
            )
        }
    }
}