package com.fitting.lenz

import android.os.Build
import androidx.annotation.RequiresApi
import com.fitting.lenz.models.ShopDetails
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.Locale
import kotlin.math.roundToInt

fun formatPaymentStatus(status: String): String {
    return if (status == "completed") "Paid"
    else "Unpaid"
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formDate(): String {
    val instant = Instant.parse(this)
    val zonedDateTime = instant.atZone(ZoneId.of("Asia/Kolkata"))
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH)
    return zonedDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toIST(): String {
    val utcDateTime = ZonedDateTime.parse(this)
    val istDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("hh:mm ")
        .appendText(ChronoField.AMPM_OF_DAY, mapOf(0L to "a.m.", 1L to "p.m."))
        .toFormatter(Locale.ENGLISH)
    return istDateTime.format(formatter)
}

fun String.findShopName(shopsList: List<ShopDetails>): String {
    var shopName = "N/A"
    for (shop in shopsList) {
        if (shop._id == this) {
            shopName = shop.name
        }
    }
    return shopName
}

fun String.checkNullorEmpty(): String {
    return this.takeUnless {
        it.isNullOrEmpty()
    } ?: "N/A"
}

fun Double.roundToTwoDecimalPlaces(): Double {
    return (this * 100).roundToInt() / 100.0
}