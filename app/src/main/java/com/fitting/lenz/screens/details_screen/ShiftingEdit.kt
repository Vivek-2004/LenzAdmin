package com.fitting.lenz.screens.details_screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel
import kotlinx.coroutines.delay
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftingEdit(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()

    var fullFrame by lenzViewModel::shiftingFullFrameCharges
    var supra by lenzViewModel::shiftingSupraCharges
    var rimLess by lenzViewModel::shiftingRimLessCharges

    var isRefreshing by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var shiftingUpdateConfirmation by lenzViewModel::shiftingUpdateConfirmation

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
                .background(colorScheme.bgColor)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shifting",
                color = colorScheme.compColor,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 18.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(colorScheme.bgColor)
                        .padding(start = 5.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Full Frame ---------->    ",
                        color = colorScheme.compColor,
                        fontSize = 22.sp
                    )
                }
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = fullFrame,
                        onValueChange = { fullFrame = it },
                        placeholder = {
                            Text(
                                text = "Enter New Price",
                                fontSize = 12.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(colorScheme.bgColor)
                        .padding(start = 5.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Supra ---------->    ",
                        color = colorScheme.compColor,
                        fontSize = 22.sp
                    )
                }
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = supra,
                        onValueChange = { supra = it },
                        placeholder = {
                            Text(
                                text = "Enter New Price",
                                fontSize = 12.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(colorScheme.bgColor)
                        .padding(start = 5.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Rimless ---------->    ",
                        color = colorScheme.compColor,
                        fontSize = 22.sp
                    )
                }
                Column(Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = rimLess,
                        onValueChange = { rimLess = it },
                        placeholder = {
                            Text(
                                text = "Enter New Price",
                                fontSize = 12.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }
            }
            Button(
                onClick = {
                    if (fullFrame.isNotEmpty() && supra.isNotEmpty() && rimLess.isNotEmpty()) {
                        isUpdating = true
                    } else {
                        Toast.makeText(context, "Enter all Fields", Toast.LENGTH_SHORT).show()
                    }
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
                        text = "Update Shifting",
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        fontSize = 24.sp,
                    )
                }
            }
        }
    }
}