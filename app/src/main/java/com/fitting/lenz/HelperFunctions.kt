package com.fitting.lenz

import android.os.Build
import androidx.annotation.RequiresApi
import com.fitting.lenz.models.ShopDetails
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatPaymentStatus(status: String): String {
    return if(status == "completed") "Paid"
    else "Unpaid"
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formDate(): String {
    val instant = Instant.parse(this)
    val zonedDateTime = instant.atZone(ZoneId.of("UTC"))
    val formatter = DateTimeFormatter.ofPattern("d'th' MMM", Locale.ENGLISH)
    return zonedDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toIST(): String {
    val utcDateTime = ZonedDateTime.parse(this)
    val istDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
    return istDateTime.format(formatter)
}

fun String.findShopName(shopsList: List<ShopDetails>): String {
    var shopName = "N/A"
    for(shop in shopsList) {
        if(shop._id == this) {
            shopName = shop.name
        }
    }
    return shopName
}