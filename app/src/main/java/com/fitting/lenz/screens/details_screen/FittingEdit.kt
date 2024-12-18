package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun FittingEdit(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    val scrollState = rememberScrollState()
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
            Modifier
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
                ShiftingFullFrame(colorScheme = colorScheme)
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
                ShiftingFullFrame(colorScheme = colorScheme)
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
                ShiftingFullFrame(colorScheme = colorScheme)
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
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 8.dp),
            onClick = {

            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorScheme.compColor,
                containerColor = Color.Gray
            )
        ) {
            Text(
                text = "Update Fitting",
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ShiftingFullFrame(
    colorScheme: ColorSchemeModel
) {
    var fullFrame by remember { mutableStateOf("15") }
    Text(
        text = "Full Frame - Normal",
        color = colorScheme.compColor,
        fontSize = 24.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(top = 24.dp),
        color = colorScheme.compColor
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Full Frame - PR",
        color = colorScheme.compColor,
        fontSize = 24.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(top = 24.dp),
        color = colorScheme.compColor
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Full Frame - Sunglass",
        color = colorScheme.compColor,
        fontSize = 24.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Low > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Single",
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
                    .padding(top = 4.dp)
            )
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .background(colorScheme.bgColor)
                .padding(start = 5.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "High > Double",
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
                    .padding(top = 4.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(top = 24.dp),
        color = colorScheme.compColor
    )
    Spacer(modifier = Modifier.height(8.dp))
}