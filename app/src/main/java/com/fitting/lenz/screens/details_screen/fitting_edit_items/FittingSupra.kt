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
fun FittingSupra(
    colorScheme: ColorSchemeModel, lenzViewModel: LenzViewModel
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

    SectionCard(
        title = "Supra - Normal", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingSupraNormal_LS,
            onValueChange = { fittingSupraNormal_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingSupraNormal_LD,
            onValueChange = { fittingSupraNormal_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingSupraNormal_HS,
            onValueChange = { fittingSupraNormal_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingSupraNormal_HD,
            onValueChange = { fittingSupraNormal_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Supra - PR", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingSupraPR_LS,
            onValueChange = { fittingSupraPR_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingSupraPR_LD,
            onValueChange = { fittingSupraPR_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingSupraPR_HS,
            onValueChange = { fittingSupraPR_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingSupraPR_HD,
            onValueChange = { fittingSupraPR_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Supra - Sunglass", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingSupraSunglass_LS,
            onValueChange = { fittingSupraSunglass_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingSupraSunglass_LD,
            onValueChange = { fittingSupraSunglass_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingSupraSunglass_HS,
            onValueChange = { fittingSupraSunglass_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingSupraSunglass_HD,
            onValueChange = { fittingSupraSunglass_HD = it },
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