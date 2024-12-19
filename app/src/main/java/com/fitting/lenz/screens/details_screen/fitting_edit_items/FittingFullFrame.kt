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
fun FittingFullFrame(
    colorScheme: ColorSchemeModel,
    lenzViewModel: LenzViewModel
) {
    var fittingFullFrameNormal_LS by lenzViewModel::fittingFullFrameNormal_LS
    var fittingFullFrameNormal_LD by lenzViewModel::fittingFullFrameNormal_LD
    var fittingFullFrameNormal_HS by lenzViewModel::fittingFullFrameNormal_HS
    var fittingFullFrameNormal_HD by lenzViewModel::fittingFullFrameNormal_HD
    var fittingFullFramePR_LS by lenzViewModel::fittingFullFramePR_LS
    var fittingFullFramePR_LD by lenzViewModel::fittingFullFramePR_LD
    var fittingFullFramePR_HS by lenzViewModel::fittingFullFramePR_HS
    var fittingFullFramePR_HD by lenzViewModel::fittingFullFramePR_HD
    var fittingFullFrameSunglass_LS by lenzViewModel::fittingFullFrameSunglass_LS
    var fittingFullFrameSunglass_LD by lenzViewModel::fittingFullFrameSunglass_LD
    var fittingFullFrameSunglass_HS by lenzViewModel::fittingFullFrameSunglass_HS
    var fittingFullFrameSunglass_HD by lenzViewModel::fittingFullFrameSunglass_HD

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
                value = fittingFullFrameNormal_LS,
                onValueChange = { fittingFullFrameNormal_LS = it },
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
                value = fittingFullFrameNormal_LD,
                onValueChange = { fittingFullFrameNormal_LD = it },
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
                value = fittingFullFrameNormal_HS,
                onValueChange = { fittingFullFrameNormal_HS = it },
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
                value = fittingFullFrameNormal_HD,
                onValueChange = { fittingFullFrameNormal_HD = it },
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
                value = fittingFullFramePR_LS,
                onValueChange = { fittingFullFramePR_LS = it },
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
                value = fittingFullFramePR_LD,
                onValueChange = { fittingFullFramePR_LD = it },
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
                value = fittingFullFramePR_HS,
                onValueChange = { fittingFullFramePR_HS = it },
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
                value = fittingFullFramePR_HD,
                onValueChange = { fittingFullFramePR_HD = it },
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
                value = fittingFullFrameSunglass_LS,
                onValueChange = { fittingFullFrameSunglass_LS = it },
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
                value = fittingFullFrameSunglass_LD,
                onValueChange = { fittingFullFrameSunglass_LD = it },
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
                value = fittingFullFrameSunglass_HS,
                onValueChange = { fittingFullFrameSunglass_HS = it },
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
                value = fittingFullFrameSunglass_HD,
                onValueChange = { fittingFullFrameSunglass_HD = it },
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