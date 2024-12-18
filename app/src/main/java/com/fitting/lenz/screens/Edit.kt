package com.fitting.lenz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.NavigationDestination

@Composable
fun Edit(
    colorScheme: ColorSchemeModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.bgColor)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Which Charges do You want to Edit ?",
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp),
            color = colorScheme.compColor
        )
        Button(
            onClick = {
                navController.navigate(NavigationDestination.ShiftingEdit.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorScheme.compColor,
                containerColor = Color.Gray.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "Edit Shifting Charges",
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        }
        Button(
            onClick = {
                navController.navigate(NavigationDestination.FittingEdit.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorScheme.compColor,
                containerColor = Color.Gray.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "Edit Fitting Charges",
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        }


        Button(
            onClick = {
                navController.navigate(NavigationDestination.History.name)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorScheme.compColor,
                containerColor = Color.Gray.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "TEST",
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                fontSize = 18.sp
            )
        }
    }
}