package com.fitting.lenz

import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminLogin(
    lenzViewModel: LenzViewModel,
    colorScheme: ColorSchemeModel,
    sharedPref: SharedPreferences,
    prefEditor: SharedPreferences.Editor
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var adminId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var loginConfirmation by remember { mutableStateOf(false) }

    val MIN_LOADING_DISPLAY_TIME = 1300L

    val response by lenzViewModel::adminConfirmation
    val loginMessage by lenzViewModel::adminLoginMessage

    LaunchedEffect(response) {
        if (response) {
            prefEditor.putBoolean("isLoggedIn", true).apply()
            loginConfirmation = sharedPref.getBoolean("isLoggedIn", false)
        }
    }

    LaunchedEffect(loginMessage) {
        when {
            loginMessage.equals("Login Successful", ignoreCase = true) -> {
            }

            loginMessage.isNotEmpty() -> {
                Toast.makeText(context, loginMessage, Toast.LENGTH_SHORT).show()
                lenzViewModel.adminLoginMessage = ""
            }
        }
    }

    if (loginConfirmation) {
        MyApp(
            colorScheme = colorScheme,
            lenzViewModelInstance = lenzViewModel
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.bgColor)
                .padding(35.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(230.dp),
                painter = painterResource(id = R.drawable.app_logo),
                tint = colorScheme.compColor,
                contentDescription = "App Logo"
            )

            Text(
                text = "Sign In to Continue",
                fontSize = 17.sp,
                modifier = Modifier.padding(bottom = 4.dp),
                color = colorScheme.compColor
            )

            OutlinedTextField(
                value = adminId,
                onValueChange = { adminId = it },
                label = { Text("Admin ID", color = colorScheme.compColor) },
                placeholder = { Text("Enter admin ID", color = colorScheme.compColor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = colorScheme.compColor) },
                placeholder = { Text("Enter password", color = colorScheme.compColor) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                trailingIcon = {
                    PasswordVisibilityToggle(
                        isVisible = isPasswordVisible,
                        onToggle = { isPasswordVisible = !isPasswordVisible },
                        colorScheme = colorScheme
                    )
                }
            )

            if (adminId.isNotEmpty() && password.isNotEmpty()) {
                LoginButton(
                    isLoading = isLoading,
                    colorScheme = colorScheme,
                    onClick = {
                        scope.launch {
                            val startTime = System.currentTimeMillis()
                            isLoading = true

                            try {
                                val adminIdInt = adminId.toIntOrNull()
                                    ?: throw NumberFormatException("Invalid admin ID")
                                withContext(Dispatchers.Main) {
                                    lenzViewModel.verifyAdmin(adminIdInt, password)
                                }
                            } catch (e: NumberFormatException) {
                                Toast.makeText(
                                    context,
                                    "Invalid Admin ID format",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } finally {
                                val elapsedTime = System.currentTimeMillis() - startTime
                                if (elapsedTime < MIN_LOADING_DISPLAY_TIME) {
                                    delay(MIN_LOADING_DISPLAY_TIME - elapsedTime)
                                }
                                isLoading = false
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun PasswordVisibilityToggle(
    isVisible: Boolean,
    onToggle: () -> Unit,
    colorScheme: ColorSchemeModel
) {
    IconButton(onClick = onToggle) {
        Icon(
            painter = if (isVisible) painterResource(R.drawable.show_password)
            else painterResource(R.drawable.hide_password),
            contentDescription = if (isVisible) "Hide password" else "Show password",
            tint = colorScheme.compColor,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun LoginButton(
    isLoading: Boolean,
    colorScheme: ColorSchemeModel,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(top = 12.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.compColor.copy(alpha = 0.9f),
            contentColor = colorScheme.bgColor
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                strokeWidth = 4.5.dp,
                color = colorScheme.bgColor
            )
        } else {
            Text(
                text = "SIGN IN",
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
                letterSpacing = 1.2.sp
            )
        }
    }
}