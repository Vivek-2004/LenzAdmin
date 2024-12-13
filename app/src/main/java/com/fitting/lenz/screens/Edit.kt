package com.fitting.lenz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun Edit(colorScheme: ColorSchemeModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorScheme.bgColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Screen",
            color = colorScheme.compColor
        )
    }
}