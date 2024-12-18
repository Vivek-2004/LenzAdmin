package com.fitting.lenz.screens.details_screen.fitting_edit_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun FittingFullFrame(
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