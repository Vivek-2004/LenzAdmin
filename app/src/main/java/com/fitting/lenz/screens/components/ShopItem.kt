package com.fitting.lenz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.ShopDetails

@Composable
fun ShopItem(
    colorScheme: ColorSchemeModel,
    shop: ShopDetails,
    navController: NavController
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
                        text = shop.name,
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
                        text = shop.phone,
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
                        text = shop.userId.toString().substring(0,6),
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
                                TODO()
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = "Orders",
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
                                TODO()
                            }
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = "History",
                        fontStyle = FontStyle.Italic,
                        color = colorScheme.compColor,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}