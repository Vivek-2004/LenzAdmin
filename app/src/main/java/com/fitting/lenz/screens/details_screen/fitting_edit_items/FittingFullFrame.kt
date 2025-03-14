package com.fitting.lenz.screens.details_screen.fitting_edit_items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.models.ColorSchemeModel

@Composable
fun FittingFullFrame(
    colorScheme: ColorSchemeModel, lenzViewModel: LenzViewModel
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

    SectionCard(
        title = "Full Frame - Normal", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingFullFrameNormal_LS,
            onValueChange = { fittingFullFrameNormal_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingFullFrameNormal_LD,
            onValueChange = { fittingFullFrameNormal_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingFullFrameNormal_HS,
            onValueChange = { fittingFullFrameNormal_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingFullFrameNormal_HD,
            onValueChange = { fittingFullFrameNormal_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Full Frame - PR", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingFullFramePR_LS,
            onValueChange = { fittingFullFramePR_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingFullFramePR_LD,
            onValueChange = { fittingFullFramePR_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingFullFramePR_HS,
            onValueChange = { fittingFullFramePR_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingFullFramePR_HD,
            onValueChange = { fittingFullFramePR_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Full Frame - Sunglass", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingFullFrameSunglass_LS,
            onValueChange = { fittingFullFrameSunglass_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingFullFrameSunglass_LD,
            onValueChange = { fittingFullFrameSunglass_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingFullFrameSunglass_HS,
            onValueChange = { fittingFullFrameSunglass_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingFullFrameSunglass_HD,
            onValueChange = { fittingFullFrameSunglass_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun SectionCard(
    title: String, colorScheme: ColorSchemeModel, content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = colorScheme.compColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = colorScheme.compColor.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun PriceInputRow(
    label: String, value: String, onValueChange: (String) -> Unit, colorScheme: ColorSchemeModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                color = colorScheme.compColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = "Enter New Price",
                        fontSize = 12.sp,
                        color = colorScheme.compColor.copy(alpha = 0.6f)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorScheme.compColor,
                    unfocusedBorderColor = colorScheme.compColor.copy(alpha = 0.5f),
                    focusedContainerColor = colorScheme.bgColor.copy(alpha = 0.1f),
                    unfocusedContainerColor = colorScheme.bgColor.copy(alpha = 0.05f),
                    focusedTextColor = colorScheme.compColor,
                    unfocusedTextColor = colorScheme.compColor
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}