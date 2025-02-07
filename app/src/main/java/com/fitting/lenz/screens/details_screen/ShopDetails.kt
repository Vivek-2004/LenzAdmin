package com.fitting.lenz.screens.details_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.formDate
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.ShopDetails

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopDetails(
    lenzViewModel: LenzViewModel,
    colorScheme: ColorSchemeModel,
    shopId: String
) {
    val shop: ShopDetails = lenzViewModel.shopsList.filter { shopId == it._id }[0]
    Column(
        modifier = Modifier.fillMaxSize().background(color = colorScheme.bgColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = shop.shopName,
                color = colorScheme.compColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(end = 30.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Member Since ${shop.createdAt.formDate()}",
                color = colorScheme.compColor,
                textAlign = TextAlign.End,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 6.dp, bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Dealer: ")
                    }
                    append(shop.name)
                },
                fontSize = 20.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("User ID: ")
                    }
                    append(shop.userId.toString())
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Phone: ")
                    }
                    append(shop.phone)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Alternate Phone: ")
                    }
                    append(shop.alternatePhone)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mail: ")
                    }
                    append(shop.email)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Distance: ")
                        }
                        append("7km")
                    },
                    fontSize = 16.sp,
                    color = colorScheme.compColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.size(17.dp).padding(top = 2.dp),
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null,
                        tint = Color.Cyan
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Address Line 1: ")
                    }
                    append(shop.address.line1)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Address Line 2: ")
                    }
                    append(shop.address.line2)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Landmark: ")
                    }
                    append(shop.address.landmark)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("City: ")
                    }
                    append(shop.address.city)
                },
                fontSize = 16.sp,
                color = colorScheme.compColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "${shop.address.state}, ${shop.address.pinCode}",
                fontSize = 16.sp,
                color = colorScheme.compColor,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}