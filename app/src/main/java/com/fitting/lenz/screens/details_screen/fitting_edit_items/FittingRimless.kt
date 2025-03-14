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
fun FittingRimless(
    colorScheme: ColorSchemeModel, lenzViewModel: LenzViewModel
) {
    var fittingRimlessNormal_LS by lenzViewModel::fittingRimlessNormal_LS
    var fittingRimlessNormal_LD by lenzViewModel::fittingRimlessNormal_LD
    var fittingRimlessNormal_HS by lenzViewModel::fittingRimlessNormal_HS
    var fittingRimlessNormal_HD by lenzViewModel::fittingRimlessNormal_HD
    var fittingRimlessPR_LS by lenzViewModel::fittingRimlessPR_LS
    var fittingRimlessPR_LD by lenzViewModel::fittingRimlessPR_LD
    var fittingRimlessPR_HS by lenzViewModel::fittingRimlessPR_HS
    var fittingRimlessPR_HD by lenzViewModel::fittingRimlessPR_HD
    var fittingRimlessSunglass_LS by lenzViewModel::fittingRimlessSunglass_LS
    var fittingRimlessSunglass_LD by lenzViewModel::fittingRimlessSunglass_LD
    var fittingRimlessSunglass_HS by lenzViewModel::fittingRimlessSunglass_HS
    var fittingRimlessSunglass_HD by lenzViewModel::fittingRimlessSunglass_HD
    var fittingRimlessPoly_LS by lenzViewModel::fittingRimlessPoly_LS
    var fittingRimlessPoly_LD by lenzViewModel::fittingRimlessPoly_LD
    var fittingRimlessPoly_HS by lenzViewModel::fittingRimlessPoly_HS
    var fittingRimlessPoly_HD by lenzViewModel::fittingRimlessPoly_HD
    var fittingRimlessPolyPR_LS by lenzViewModel::fittingRimlessPolyPR_LS
    var fittingRimlessPolyPR_LD by lenzViewModel::fittingRimlessPolyPR_LD
    var fittingRimlessPolyPR_HS by lenzViewModel::fittingRimlessPolyPR_HS
    var fittingRimlessPolyPR_HD by lenzViewModel::fittingRimlessPolyPR_HD

    SectionCard(
        title = "Rimless - Normal", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingRimlessNormal_LS,
            onValueChange = { fittingRimlessNormal_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingRimlessNormal_LD,
            onValueChange = { fittingRimlessNormal_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingRimlessNormal_HS,
            onValueChange = { fittingRimlessNormal_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingRimlessNormal_HD,
            onValueChange = { fittingRimlessNormal_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Rimless - PR", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingRimlessPR_LS,
            onValueChange = { fittingRimlessPR_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingRimlessPR_LD,
            onValueChange = { fittingRimlessPR_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingRimlessPR_HS,
            onValueChange = { fittingRimlessPR_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingRimlessPR_HD,
            onValueChange = { fittingRimlessPR_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Rimless - Sunglass", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingRimlessSunglass_LS,
            onValueChange = { fittingRimlessSunglass_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingRimlessSunglass_LD,
            onValueChange = { fittingRimlessSunglass_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingRimlessSunglass_HS,
            onValueChange = { fittingRimlessSunglass_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingRimlessSunglass_HD,
            onValueChange = { fittingRimlessSunglass_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Rimless - Poly", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingRimlessPoly_LS,
            onValueChange = { fittingRimlessPoly_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingRimlessPoly_LD,
            onValueChange = { fittingRimlessPoly_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingRimlessPoly_HS,
            onValueChange = { fittingRimlessPoly_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingRimlessPoly_HD,
            onValueChange = { fittingRimlessPoly_HD = it },
            colorScheme = colorScheme
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    SectionCard(
        title = "Rimless - PolyPR", colorScheme = colorScheme
    ) {
        PriceInputRow(
            label = "Low > Single",
            value = fittingRimlessPolyPR_LS,
            onValueChange = { fittingRimlessPolyPR_LS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "Low > Double",
            value = fittingRimlessPolyPR_LD,
            onValueChange = { fittingRimlessPolyPR_LD = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Single",
            value = fittingRimlessPolyPR_HS,
            onValueChange = { fittingRimlessPolyPR_HS = it },
            colorScheme = colorScheme
        )

        PriceInputRow(
            label = "High > Double",
            value = fittingRimlessPolyPR_HD,
            onValueChange = { fittingRimlessPolyPR_HD = it },
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