package com.fitting.lenz.screens.details_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftingEdit(
    colorScheme: ColorSchemeModel, lenzViewModel: LenzViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()

    var fullFrame by lenzViewModel::shiftingFullFrameCharges
    var supra by lenzViewModel::shiftingSupraCharges
    var rimLess by lenzViewModel::shiftingRimLessCharges
    var shiftingUpdateConfirmation by lenzViewModel::shiftingUpdateConfirmation

    var isRefreshing by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirm Price Update", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    text = "Are you sure you want to Update the Charges?", fontSize = 15.sp
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    isUpdating = true
                    showDialog = false
                }) {
                    Text(text = "Confirm", fontSize = 16.sp, color = colorScheme.compColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "Cancel", fontSize = 16.sp)
                }
            },
            containerColor = colorScheme.bgColor.copy(alpha = 0.95f),
            shape = RoundedCornerShape(16.dp)
        )
    }

    if (isRefreshing) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                lenzViewModel.getShiftingCharges()
            }
            delay(2000L)
            isRefreshing = false
        }
    }

    if (isUpdating) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                lenzViewModel.updateShiftingCharges()
            }
            delay(1500L)
            isUpdating = false
            isRefreshing = true
            if (shiftingUpdateConfirmation) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show()
                    shiftingUpdateConfirmation = false
                }
            }
        }
    }

    PullToRefreshBox(
        state = pullToRefreshState, isRefreshing = isRefreshing, onRefresh = {
            isRefreshing = true
        }, modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.bgColor)
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shifting Charges",
                color = colorScheme.compColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 24.dp)
            )

            Text(
                text = "Edit Shifting Charges...",
                color = colorScheme.compColor.copy(alpha = 0.7f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .shadow(elevation = 18.dp, shape = RoundedCornerShape(16.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 18.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PriceInputRow(
                        label = "Full Frame",
                        value = fullFrame,
                        onValueChange = { fullFrame = it },
                        colorScheme = colorScheme
                    )

                    HorizontalDivider(
                        thickness = 1.dp, color = colorScheme.compColor.copy(alpha = 0.2f)
                    )

                    PriceInputRow(
                        label = "Supra",
                        value = supra,
                        onValueChange = { supra = it },
                        colorScheme = colorScheme
                    )

                    HorizontalDivider(
                        thickness = 1.dp, color = colorScheme.compColor.copy(alpha = 0.2f)
                    )

                    PriceInputRow(
                        label = "Rimless",
                        value = rimLess,
                        onValueChange = { rimLess = it },
                        colorScheme = colorScheme
                    )
                }
            }

            Button(
                onClick = {
                    if (fullFrame.isNotEmpty() && supra.isNotEmpty() && rimLess.isNotEmpty()) {
                        showDialog = true
                    } else {
                        Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = colorScheme.compColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp, pressedElevation = 8.dp
                )
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp), strokeWidth = 3.dp, color = Color.White
                    )
                } else {
                    Text(
                        text = "Update Shifting Charges",
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun PriceInputRow(
    label: String, value: String, onValueChange: (String) -> Unit, colorScheme: ColorSchemeModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = colorScheme.compColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Enter Price",
                    fontSize = 14.sp,
                    color = colorScheme.compColor.copy(alpha = 0.5f)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.compColor,
                unfocusedBorderColor = colorScheme.compColor.copy(alpha = 0.5f),
                cursorColor = colorScheme.compColor
            )
        )
    }
}