package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun History(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorScheme.bgColor).verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(
//            text = "History Screen",
//            color = colorScheme.compColor
//        )

                Text(
            text = lenzViewModel.test,
            color = colorScheme.compColor,
                    fontSize = 12.sp
        )

    }
}