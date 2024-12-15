package com.fitting.lenz.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.components.OrderItem

@Composable
fun Orders(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel = viewModel()
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(15) { index ->
            OrderItem(
                colorScheme = colorScheme,
                lenzViewModel = lenzViewModel,
                testOrderIndex = (index + 1) * 100 + (Math.random()*100.00).toInt()
            )
            HorizontalDivider(
                modifier = Modifier.width(320.dp),
                color = colorScheme.compColor.copy(alpha = 0.1f),
                thickness = 1.dp
            )
        }
    }
}