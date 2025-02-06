package com.fitting.lenz

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.MyApp
import com.fitting.lenz.ui.theme.LenzTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        val sharedPref = getSharedPreferences("LenzAdmin", Context.MODE_PRIVATE)
        val prefEditor = sharedPref.edit()

        if (!sharedPref.contains("isLoggedIn")) {
            prefEditor.putBoolean("isLoggedIn", false)
            prefEditor.apply()
        }

        setContent {
            LenzTheme {
                val isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }

                var colorScheme = ColorSchemeModel(
                    compColor = Color.Black,
                    bgColor = Color.White
                )
                if (isSystemInDarkTheme()) {
                    colorScheme = ColorSchemeModel(
                        compColor = Color.White,
                        bgColor = Color.Black
                    )
                }

                if (isLoggedIn) {
                    MyApp( colorScheme = colorScheme )
                } else {
                    AdminLogin(
                        colorScheme = colorScheme,
                        sharedPref = sharedPref,
                        prefEditor = prefEditor
                    )
                }
            }
        }
    }
}