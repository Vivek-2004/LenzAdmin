package com.fitting.lenz.screens.details_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingFullFrame
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingRimless
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingSupra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FittingEdit(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()

    var isRefreshing by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var fittingUpdateConfirmation by lenzViewModel::fittingUpdateConfirmation

    if(showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirm Price Update") },
            text = { Text(text = "Are you sure you want to Update the Charges?", fontSize = 15.sp) },
            confirmButton = {
                TextButton(onClick = {
                    isUpdating = true
                    showDialog = false
                }) {
                    Text(text = "Confirm", fontSize = 16.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            }
        )
    }

    if (isRefreshing) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                lenzViewModel.getFittingCharges()
            }
            delay(2000L)
            isRefreshing = false
        }
    }

    if (isUpdating) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                lenzViewModel.updateFittingCharges()
            }
            delay(1500L)
            isUpdating = false
            isRefreshing = true
            if (fittingUpdateConfirmation) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show()
                    fittingUpdateConfirmation = false
                }
            }
        }
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        },
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor.copy(alpha = 0.1f))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(colorScheme.bgColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showFullFrame by remember { mutableStateOf(false) }
            var showSupra by remember { mutableStateOf(false) }
            var showRimless by remember { mutableStateOf(false) }
            Text(
                text = "Fitting",
                color = colorScheme.compColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 18.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        showFullFrame = !showFullFrame
                        showSupra = false
                        showRimless = false
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showFullFrame) {
                    FittingFullFrame(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModel
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .width(320.dp)
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(vertical = 13.dp, horizontal = 24.dp),
                        text = "Edit Full Frame Charges",
                        color = colorScheme.compColor,
                        fontSize = 18.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        showSupra = !showSupra
                        showFullFrame = false
                        showRimless = false
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showSupra) {
                    FittingSupra(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModel
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(vertical = 13.dp, horizontal = 24.dp)
                            .padding(start = 18.dp),
                        text = "Edit Supra Charges",
                        color = colorScheme.compColor,
                        fontSize = 18.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        showRimless = !showRimless
                        showFullFrame = false
                        showSupra = false
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showRimless) {
                    FittingRimless(
                        colorScheme = colorScheme,
                        lenzViewModel = lenzViewModel
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .width(300.dp)
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .padding(vertical = 13.dp, horizontal = 24.dp)
                            .padding(start = 12.dp),
                        text = "Edit Rimless Charges",
                        color = colorScheme.compColor,
                        fontSize = 18.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = {
                    showDialog = true
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorScheme.compColor,
                    containerColor = Color.Gray.copy(alpha = 0.5f)
                )
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 5.dp,
                    )
                } else {
                    Text(
                        text = "Update Fitting",
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        fontSize = 24.sp,
                    )
                }
            }
        }
    }
}