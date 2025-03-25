package com.fitting.lenz.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import kotlinx.coroutines.delay

@Composable
fun AdminProfile(
    lenzViewModel: LenzViewModel
) {
    val scrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(true) }
    val adminDetailsState by lenzViewModel.adminDetails.collectAsState()

    LaunchedEffect(isLoading) {
        lenzViewModel.getAdminDetails()
        if (!isLoading) return@LaunchedEffect
        delay(1500)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when {
            adminDetailsState == null -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                adminDetailsState?.let { admin ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {
                        AdminInfoCard(
                            title = "Name", value = admin.name, icon = Icons.Default.AccountCircle
                        )
                        AdminInfoCard(
                            title = "Email", value = admin.email, icon = Icons.Default.Email
                        )
                        AdminInfoCard(
                            title = "Phone",
                            value = admin.phone.takeLast(10),
                            icon = Icons.Default.Phone
                        )
                        Card(
                            shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray.copy(
                                    alpha = 0.3f
                                )
                            ), border = BorderStroke(
                                width = 3.dp, color = Color.Black
                            ), modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = "Admin ID",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "LenZ Admin ID",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = admin.adminId.toString(),
                                        fontSize = 14.sp,
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.LightGray.copy(
                                        alpha = 0.3f
                                    )
                                ),
                                border = BorderStroke(
                                    width = 3.dp, color = Color.Gray
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3f)
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Key,
                                            contentDescription = "Auth Token",
                                            modifier = Modifier.size(24.dp),
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Auth Token",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                    if (isLoading) {
                                        LinearProgressIndicator(
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    } else {
                                        Text(
                                            text = admin.authToken,
                                            fontSize = 14.sp,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.LightGray.copy(
                                        alpha = 0.3f
                                    )
                                ),
                                border = BorderStroke(
                                    width = 3.dp, color = Color.Red.copy(alpha = 0.4f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(72.dp)
                                        .align(Alignment.CenterHorizontally),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        Icon(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(11.dp)
                                                .clickable { isLoading = true },
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "Refresh Auth Token",
                                            tint = Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Address",
                                modifier = Modifier.size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Address",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        with(admin.address) {
                            AdminInfoCard(
                                title = "Line 1", value = line1, icon = Icons.Default.Home
                            )
                            AdminInfoCard(
                                title = "Line 2", value = line2, icon = Icons.Default.Home
                            )
                            AdminInfoCard(
                                title = "Landmark",
                                value = landmark,
                                icon = Icons.Default.LocationOn
                            )
                            AdminInfoCard(
                                title = "City", value = city, icon = Icons.Default.LocationCity
                            )
                            AdminInfoCard(
                                title = "State", value = state, icon = Icons.Default.LocationCity
                            )
                            AdminInfoCard(
                                title = "Pin Code", value = pinCode, icon = Icons.Default.Pin
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminInfoCard(
    title: String, value: String, icon: ImageVector
) {
    Card(
        shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(
                alpha = 0.3f
            )
        ), border = BorderStroke(
            width = 1.dp, color = Color.Gray.copy(alpha = 0.5f)
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
                Text(
                    text = value, fontSize = 14.sp, color = Color.DarkGray
                )
            }
        }
    }
}