package com.fitting.lenz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.ShopDetails

@Composable
fun ShopItem(
    colorScheme: ColorSchemeModel,
    shop: ShopDetails,
    onShopCardClick: () -> Unit,
    shopsOnClick: () -> Unit,
    historyOnClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = colorScheme.compColor.copy(alpha = 0.25f)
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White
                )
                .clickable { onShopCardClick() }) {
            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header with shop name and plan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shop icon with circle background
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = colorScheme.compColor.copy(alpha = 0.15f),
                                shape = CircleShape
                            ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Store,
                            contentDescription = "Shop",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = shop.shopName,
                        color = colorScheme.compColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp), color = Color.DarkGray
                        ) {
                            Text(
                                text = shop.plan,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contact details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Phone number
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = colorScheme.compColor.copy(alpha = 0.1f),
                                    shape = CircleShape
                                ), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = "Phone",
                                tint = colorScheme.compColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = shop.phone.takeLast(10),
                            color = colorScheme.compColor.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = colorScheme.compColor.copy(alpha = 0.1f),
                                    shape = CircleShape
                                ), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.hash),
                                contentDescription = "ID",
                                tint = colorScheme.compColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = shop.userId.toString(),
                            color = colorScheme.compColor.copy(alpha = 0.9f),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Pending Orders Button
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { shopsOnClick() },
                        color = colorScheme.compColor.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Pending Orders",
                                fontWeight = FontWeight.Medium,
                                color = colorScheme.compColor,
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Order History Button
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { historyOnClick() },
                        color = colorScheme.compColor.copy(alpha = 0.12f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Order History",
                                fontWeight = FontWeight.Medium,
                                color = colorScheme.compColor,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}