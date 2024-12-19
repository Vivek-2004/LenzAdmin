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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun FittingSupra(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    var fittingSupraNormal_LS by lenzViewModel::fittingSupraNormal_LS
    var fittingSupraNormal_LD by lenzViewModel::fittingSupraNormal_LD
    var fittingSupraNormal_HS by lenzViewModel::fittingSupraNormal_HS
    var fittingSupraNormal_HD by lenzViewModel::fittingSupraNormal_HD
    var fittingSupraPR_LS by lenzViewModel::fittingSupraPR_LS
    var fittingSupraPR_LD by lenzViewModel::fittingSupraPR_LD
    var fittingSupraPR_HS by lenzViewModel::fittingSupraPR_HS
    var fittingSupraPR_HD by lenzViewModel::fittingSupraPR_HD
    var fittingSupraSunglass_LS by lenzViewModel::fittingSupraSunglass_LS
    var fittingSupraSunglass_LD by lenzViewModel::fittingSupraSunglass_LD
    var fittingSupraSunglass_HS by lenzViewModel::fittingSupraSunglass_HS
    var fittingSupraSunglass_HD by lenzViewModel::fittingSupraSunglass_HD

    Text(
        text = "Supra - Normal",
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
                value = fittingSupraNormal_LS,
                onValueChange = { fittingSupraNormal_LS = it },
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
                value = fittingSupraNormal_LD,
                onValueChange = { fittingSupraNormal_LD = it },
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
                value = fittingSupraNormal_HS,
                onValueChange = { fittingSupraNormal_HS = it },
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
                value = fittingSupraNormal_HD,
                onValueChange = { fittingSupraNormal_HD = it },
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
        text = "Supra - PR",
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
                value = fittingSupraPR_LS,
                onValueChange = { fittingSupraPR_LS = it },
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
                value = fittingSupraPR_LD,
                onValueChange = { fittingSupraPR_LD = it },
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
                value = fittingSupraPR_HS,
                onValueChange = { fittingSupraPR_HS = it },
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
                value = fittingSupraPR_HD,
                onValueChange = { fittingSupraPR_HD = it },
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
        text = "Supra - Sunglass",
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
                value = fittingSupraSunglass_LS,
                onValueChange = { fittingSupraSunglass_LS = it },
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
                value = fittingSupraSunglass_LD,
                onValueChange = { fittingSupraSunglass_LD = it },
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
                value = fittingSupraSunglass_HS,
                onValueChange = { fittingSupraSunglass_HS = it },
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
                value = fittingSupraSunglass_HD,
                onValueChange = { fittingSupraSunglass_HD = it },
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