package com.fitting.lenz.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fitting.lenz.LenzViewModel

@Composable
fun AdminProfile(lenzViewModel: LenzViewModel) {
    val adminDetailsState by lenzViewModel.adminDetails.collectAsState()
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        when {
            adminDetailsState == null -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                adminDetailsState?.let { admin ->
                    println(admin)
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(admin.toString())
                    }
                }
            }
        }
    }
}