package com.fitting.lenz.screens.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun ShiftingEdit(colorScheme: ColorSchemeModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(colorScheme.bgColor).padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var fullFrame by remember { mutableStateOf("15") }
        var supra by remember { mutableStateOf("20") }
        var rimLess by remember { mutableStateOf("40") }

        Text(
            text = "Shifting",
            color = colorScheme.compColor,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 18.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f).background(colorScheme.bgColor).padding(start = 5.dp,top = 10.dp),
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
                    placeholder = { Text(
                        text = "Enter New Price",
                        fontSize = 12.sp
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f).background(colorScheme.bgColor).padding(start = 5.dp,top = 10.dp),
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
                    placeholder = { Text(
                        text = "Enter New Price",
                        fontSize = 12.sp
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f).background(colorScheme.bgColor).padding(start = 5.dp,top = 10.dp),
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
                    placeholder = { Text(
                        text = "Enter New Price",
                        fontSize = 12.sp
                    ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        }
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
                containerColor = Color.Gray.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "Update Shifting",
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp
            )
        }
    }
}