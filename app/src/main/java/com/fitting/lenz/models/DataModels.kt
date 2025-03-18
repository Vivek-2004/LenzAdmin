package com.fitting.lenz.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StatusInfo(
    val status: String,
    val backgroundColor: Color,
    val textColor: Color,
    val iconTint: Color,
    val icon: ImageVector
)

data class RiderCounts(
    val availableCount: Int,
    val busyCount: Int,
    val notWorkingCount: Int
)

data class AnimationValues(
    val availablePulse: Float,
    val busyPulse: Float,
    val notWorkingPulse: Float,
    val availableScale: Float,
    val busyScale: Float,
    val notWorkingScale: Float
)