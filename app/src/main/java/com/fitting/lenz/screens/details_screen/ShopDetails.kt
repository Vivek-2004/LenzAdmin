package com.fitting.lenz.screens.details_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.formDate
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.models.ShopDetails

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShopDetails(
    lenzViewModel: LenzViewModel = viewModel(),
    colorScheme: ColorSchemeModel,
    shopId: String
) {
    val shop: ShopDetails = lenzViewModel.shopsList.filter { shopId == it._id }[0]
    Column(
        modifier = Modifier.fillMaxSize()
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
                text = "Dealer: ${shop.name}",
                color = colorScheme.compColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "User ID: ${shop.userId}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "Phone: ${shop.phone}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "Alternate Phone: ${shop.alternatePhone}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "Mail: ${shop.email}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "Distance: 7km",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "Address Line 1: ${shop.address.line1}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 20.dp),
                text = "Address Line 2: ${shop.address.line2}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "${shop.address.landmark}, ${shop.address.city}",
                color = colorScheme.compColor,
            )
            Text(
                modifier = Modifier.padding(bottom = 3.dp),
                text = "${shop.address.state}, ${shop.address.pinCode}",
                color = colorScheme.compColor,
            )
        }
    }
}