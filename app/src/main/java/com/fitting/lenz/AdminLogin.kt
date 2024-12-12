package com.fitting.lenz

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminLogin(
    lenzViewModel: LenzViewModel = viewModel(),
    sharedPref: SharedPreferences,
    prefEditor: SharedPreferences.Editor
) {
    val context = LocalContext.current
    var bgColor = Color.White
    var compColor = Color.Black

    if(isSystemInDarkTheme()) {
        bgColor = Color.Black
        compColor = Color.White
    }

    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val response by lenzViewModel::adminConfirmation

    var loginConfirmation by remember { mutableStateOf(false) }

    if(response) {
        prefEditor.putBoolean("isLoggedIn", true)
        prefEditor.apply()
        loginConfirmation = sharedPref.getBoolean("isLoggedIn", false)
    }

    if(loginConfirmation) {
        BottomNavigationBarExample()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 12.dp),
                painter = painterResource(id = R.drawable.app_logo),
                tint = compColor,
                contentDescription = "logo",
            )

            Text(
                text = "Login to Continue",
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 12.dp),
                color = compColor
            )

            OutlinedTextField(
                value = adminId,
                onValueChange = { adminId = it },
                label = { Text("ID") },
                placeholder = { Text("Enter your ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            painter = if (isPasswordVisible) painterResource(R.drawable.show_password)
                            else painterResource(R.drawable.hide_password),
                            contentDescription = "passwordToggle"
                        )
                    }
                },
            )

            Button(
                onClick = {
                    Toast.makeText(context,adminId + password, Toast.LENGTH_LONG).show()
                    lenzViewModel.verifyAdmin(
                        id = adminId.toInt(),
                        pass = password
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = bgColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Black,
                    color = compColor,
                    fontSize = 24.sp
                )
            }
        }
    }
}