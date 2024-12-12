package com.fitting.lenz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.fitting.lenz.ui.theme.LenzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("LenzPref", Context.MODE_PRIVATE)
        val prefEditor = sharedPref.edit()

        if (!sharedPref.contains("isLoggedIn")) {
            prefEditor.putBoolean("isLoggedIn", false)
            prefEditor.apply()
        }

        setContent {
            LenzTheme {
                MyApp(
                    sharedPref = sharedPref,
                    prefEditor = prefEditor
                )
            }
        }
//        val serviceIntent = Intent(this, ForegroundService::class.java)
//        startService(serviceIntent)
    }
}

@Composable
fun MyApp(
    sharedPref: SharedPreferences,
    prefEditor: SharedPreferences.Editor
) {
    val isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }

    if (isLoggedIn) {
        BottomNavigationBarExample()
    } else {
        AdminLogin(
            sharedPref = sharedPref,
            prefEditor = prefEditor
        )
    }
}