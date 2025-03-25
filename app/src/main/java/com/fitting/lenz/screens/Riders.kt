package com.fitting.lenz.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.DirectionsBike
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.AnimationValues
import com.fitting.lenz.models.RiderCounts
import com.fitting.lenz.models.RiderDetails
import com.fitting.lenz.models.StatusInfo
import com.fitting.lenz.navigation.NavigationDestination
import kotlinx.coroutines.delay

private val LightGray = Color(0xFFF8F9FA)
private val MediumGray = Color(0xFFE0E0E0)
private val DarkTextColor = Color(0xFF2C3E50)
private val SecondaryTextColor = Color(0xFF7F8C8D)
private val BluePrimary = Color(0xFF3498DB)
private val BlueDark = Color(0xFF2980B9)
private val BorderColor = Color(0xFFF3F4F6)
private val GreenAvailable = Color(0xFF4CAF50)
private val GreenBackgroundAvailable = Color(0xFFE8F5E9)
private val GreenTextAvailable = Color(0xFF2E7D32)
private val RedInactive = Color(0xFFF44336)
private val RedBackgroundInactive = Color(0xFFFFEBEE)
private val RedTextInactive = Color(0xFFC62828)
private val AmberBusy = Color(0xFFFFA000)
private val AmberBackgroundBusy = Color(0xFFFFF8E1)
private val AmberTextBusy = Color(0xFFE65100)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Riders(
    navController: NavController,
    lenzViewModel: LenzViewModel
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val backgroundGradient = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color.DarkGray,
                Color.Gray,
                LightGray
            )
        )
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        lenzViewModel.getAllRiders()
        delay(1200)
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    RidersStatusSummary(ridersList = lenzViewModel.ridersList)
                }
                itemsIndexed(lenzViewModel.ridersList) { index, rider ->
                    RiderCard(
                        rider = rider,
                        onClick = {
                            navController.navigate("${NavigationDestination.RiderDetails.name}/${rider.riderId}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RiderCard(rider: RiderDetails, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0x40000000)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with rider avatar and info
            RiderHeader(rider)

            Spacer(modifier = Modifier.height(16.dp))

            // Status indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusIndicator(rider = rider)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats section
            StatsSection(rider)
        }
    }
}

@Composable
private fun RiderHeader(rider: RiderDetails) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(BluePrimary, BlueDark)
                    )
                )
                .border(2.dp, BorderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = "Rider",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Rider details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = rider.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "#${rider.riderId}",
                    fontSize = 14.sp,
                    color = SecondaryTextColor
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.DirectionsBike,
                        contentDescription = "Vehicle",
                        tint = BluePrimary,
                        modifier = Modifier.size(14.dp)
                    )

                    Text(
                        text = rider.vehicleNumber,
                        fontSize = 14.sp,
                        color = SecondaryTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsSection(rider: RiderDetails) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LightGray
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatItem(
                label = "Total Orders",
                value = rider.totalOrders.toString(),
                valueColor = DarkTextColor
            )

            HorizontalDivider(
                modifier = Modifier
                    .height(36.dp)
                    .width(1.dp),
                color = MediumGray
            )

            StatItem(
                label = "Total Earnings",
                value = "₹${rider.totalEarnings}",
                valueColor = Color(0xFF27AE60)
            )
        }
    }
}

@Composable
fun StatusIndicator(rider: RiderDetails) {
    val (status, backgroundColor, textColor, iconTint, icon) = remember(
        rider.isWorking,
        rider.isAvailable
    ) {
        when {
            !rider.isWorking -> StatusInfo(
                "Inactive",
                RedBackgroundInactive,
                RedTextInactive,
                RedInactive,
                Icons.Rounded.Cancel
            )

            rider.isWorking && rider.isAvailable -> StatusInfo(
                "Available",
                GreenBackgroundAvailable,
                GreenTextAvailable,
                GreenAvailable,
                Icons.Rounded.CheckCircle
            )

            else -> StatusInfo(
                "Busy",
                AmberBackgroundBusy,
                AmberTextBusy,
                AmberBusy,
                Icons.AutoMirrored.Rounded.DirectionsBike
            )
        }
    }

    val scaleAnimation by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .scale(scaleAnimation),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = status,
            tint = iconTint,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = status,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun StatItem(label: String, value: String, valueColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = SecondaryTextColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Composable
fun RidersStatusSummary(ridersList: List<RiderDetails>) {
    val riderCounts by remember(ridersList) {
        derivedStateOf {
            RiderCounts(
                availableCount = ridersList.count { it.isWorking && it.isAvailable },
                busyCount = ridersList.count { it.isWorking && !it.isAvailable },
                notWorkingCount = ridersList.count { !it.isWorking }
            )
        }
    }

    // State for animation
    var pulseState by remember { mutableIntStateOf(0) }

    // Animation effect
    LaunchedEffect(key1 = true) {
        while (true) {
            pulseState = (pulseState + 1) % 3
            delay(1000) // Update every second
        }
    }

    // Animation values
    val animationValues = createAnimationValues(pulseState)

    // Total riders count card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1F5F9)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Riders header
            RiderSummaryHeader(ridersList.size)

            Spacer(modifier = Modifier.height(12.dp))

            // Status indicators with counts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Available riders
                StatusCounter(
                    color = GreenAvailable.copy(alpha = animationValues.availablePulse),
                    icon = Icons.Rounded.CheckCircle,
                    label = "Available",
                    count = riderCounts.availableCount,
                    scale = animationValues.availableScale
                )

                // Busy riders
                StatusCounter(
                    color = AmberBusy.copy(alpha = animationValues.busyPulse),
                    icon = Icons.AutoMirrored.Rounded.DirectionsBike,
                    label = "Busy",
                    count = riderCounts.busyCount,
                    scale = animationValues.busyScale
                )

                // Inactive riders
                StatusCounter(
                    color = RedInactive.copy(alpha = animationValues.notWorkingPulse),
                    icon = Icons.Rounded.Cancel,
                    label = "Inactive",
                    count = riderCounts.notWorkingCount,
                    scale = animationValues.notWorkingScale
                )
            }
        }
    }
}

@Composable
private fun RiderSummaryHeader(totalRiders: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        // Circle with rider count
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(BluePrimary)
                .border(2.dp, Color.DarkGray, CircleShape)
        ) {
            Text(
                text = "$totalRiders",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Text "Riders registered"
        Text(
            text = "Riders Registered",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155)
        )
    }
}

@Composable
private fun createAnimationValues(pulseState: Int): AnimationValues {
    val availablePulse by animateFloatAsState(
        targetValue = if (pulseState == 0) 1f else 0.5f,
        animationSpec = spring(dampingRatio = 0.8f),
        label = "availablePulse"
    )

    val busyPulse by animateFloatAsState(
        targetValue = if (pulseState == 1) 1f else 0.5f,
        animationSpec = spring(dampingRatio = 0.8f),
        label = "busyPulse"
    )

    val notWorkingPulse by animateFloatAsState(
        targetValue = if (pulseState == 2) 1f else 0.5f,
        animationSpec = spring(dampingRatio = 0.8f),
        label = "notWorkingPulse"
    )

    val availableScale by animateFloatAsState(
        targetValue = if (pulseState == 0) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "availableScale"
    )

    val busyScale by animateFloatAsState(
        targetValue = if (pulseState == 1) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "busyScale"
    )

    val notWorkingScale by animateFloatAsState(
        targetValue = if (pulseState == 2) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "notWorkingScale"
    )

    return AnimationValues(
        availablePulse, busyPulse, notWorkingPulse,
        availableScale, busyScale, notWorkingScale
    )
}

@Composable
fun StatusCounter(color: Color, icon: ImageVector, label: String, count: Int, scale: Float = 1f) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.scale(scale) // Apply scale animation
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = count.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF64748B)
        )
    }
}