package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fitting.lenz.LenzViewModel

@Composable
fun ActiveOrders(
    lenzViewModel: LenzViewModel
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text("Vivek")
    }
}