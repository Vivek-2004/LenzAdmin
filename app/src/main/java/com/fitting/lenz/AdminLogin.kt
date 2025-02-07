package com.fitting.lenz

import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminLogin(
    lenzViewModel: LenzViewModel = viewModel(),
    colorScheme: ColorSchemeModel,
    sharedPref: SharedPreferences,
    prefEditor: SharedPreferences.Editor
) {
    val context = LocalContext.current
    val response by lenzViewModel::adminConfirmation

    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var loginConfirmation by remember { mutableStateOf(false) }

    if(response) {
        prefEditor.putBoolean("isLoggedIn", true)
        prefEditor.apply()
        loginConfirmation = sharedPref.getBoolean("isLoggedIn", false)
    }

    if(loginConfirmation) {
        MyApp( colorScheme = colorScheme )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.bgColor)
                .padding(30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(220.dp),
                painter = painterResource(id = R.drawable.app_logo),
                tint = colorScheme.compColor,
                contentDescription = "logo",
            )

            Text(
                text = "Login to Continue",
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(bottom = 14.dp),
                color = colorScheme.compColor
            )

            OutlinedTextField(
                value = adminId,
                onValueChange = { adminId = it },
                label = { Text(
                    text = "ID",
                    color = colorScheme.compColor
                ) },
                placeholder = {
                    Text(
                        text = "Enter your ID",
                        color = colorScheme.compColor
                    ) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(color = colorScheme.compColor),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(
                    text = "Password",
                    color = colorScheme.compColor
                ) },
                placeholder = { Text(
                    text = "Enter your password",
                    color = colorScheme.compColor
                ) },
                textStyle = TextStyle(color = colorScheme.compColor),
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
                }
            )

            if(adminId.isNotEmpty() && password.isNotEmpty()) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(top = 12.dp).clickable {  },
                    onClick = {
                        if(adminId.isNotEmpty() && password.isNotEmpty()) {
                            lenzViewModel.verifyAdmin(
                                id = adminId.toInt(),
                                pass = password
                            )
                            isLoading = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(3000L)
                                if (!loginConfirmation) {
                                    isLoading = false
                                    Toast.makeText(context,"Incorrect ID or Password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context,"Enter Details to Continue", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = colorScheme.compColor,
                        containerColor = Color.Gray.copy(alpha = 0.3f)
                    )
                ) {
                    if(isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            strokeWidth = 5.dp
                        )
                    }
                    else {
                        Text(
                            text = "Login",
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}