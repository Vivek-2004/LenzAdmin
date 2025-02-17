package com.fitting.lenz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .padding(start = 13.dp, end = 13.dp, bottom = 6.dp, top = 4.dp)
            .height(130.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.bgColor)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().clickable {
                    onShopCardClick()
                }
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = shop.shopName,
                            color = colorScheme.compColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = shop.plan,
                            color = colorScheme.compColor,
                            fontSize = 16.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = "+91-${shop.phone.takeLast(10)}",
                            color = colorScheme.compColor,
                            fontSize = 16.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = shop.userId.toString().substring(0, 6),
                            color = colorScheme.compColor,
                            fontSize = 16.sp
                        )
                    }
                }
                HorizontalDivider(thickness = 0.35.dp)
                Row(
                    modifier = Modifier
                        .weight(0.6f)
                        .background(Color.LightGray.copy(alpha = 0.4f)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                            .clickable(
                                onClick = {
                                    shopsOnClick()
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 2.dp),
                            text = "Pending Orders",
                            fontStyle = FontStyle.Italic,
                            color = colorScheme.compColor,
                            fontSize = 14.sp
                        )
                    }
                    VerticalDivider()
                    Column(
                        modifier = Modifier.weight(1f)
                            .clickable(
                                onClick = {
                                    historyOnClick()
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 2.dp),
                            text = "Order History",
                            fontStyle = FontStyle.Italic,
                            color = colorScheme.compColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}