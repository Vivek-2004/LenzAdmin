package com.fitting.lenz.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    val scrollBarWidth = 5.dp

    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .drawBehind {
            val elementHeight = this.size.height / listState.layoutInfo.totalItemsCount
            val offset = listState.layoutInfo.visibleItemsInfo.first().index * elementHeight
            val scrollbarHeight = listState.layoutInfo.visibleItemsInfo.size * elementHeight
            drawRect(
                color = colorScheme.compColor.copy(alpha = 0.1f),
                topLeft = Offset(this.size.width - scrollBarWidth.toPx(), offset),
                size = Size(scrollBarWidth.toPx(), scrollbarHeight)
            )
        }.padding(end = scrollBarWidth)
    ) {
        items(25) { index ->
            OrderItem(
                colorScheme = colorScheme,
                lenzViewModel = lenzViewModel
            )
            HorizontalDivider(
                modifier = Modifier.width(320.dp),
                color = colorScheme.compColor.copy(alpha = 0.1f),
                thickness = 1.dp
            )
        }
    }
}