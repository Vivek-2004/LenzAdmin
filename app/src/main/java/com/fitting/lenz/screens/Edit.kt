package com.fitting.lenz.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.navigation.NavigationDestination
import kotlinx.coroutines.delay

@Composable
fun Edit(
    colorScheme: ColorSchemeModel,
    navController: NavController
) {
    val titleVisibleState = remember { MutableTransitionState(false) }
    val buttonsVisibleState = remember { MutableTransitionState(false) }

    LaunchedEffect(Unit) {
        titleVisibleState.targetState = true
        delay(300)
        buttonsVisibleState.targetState = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated title card
            AnimatedVisibility(
                visibleState = titleVisibleState,
                enter = fadeIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) + slideInVertically(
                    initialOffsetY = { -100 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.bgColor
                    )
                ) {
                    Text(
                        text = "Which Charges do You want to Edit?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(24.dp),
                        color = colorScheme.compColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated buttons
            AnimatedVisibility(
                visibleState = buttonsVisibleState,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EditOptionButton(
                        text = "Edit Shifting Charges",
                        icon = Icons.Default.Edit,
                        colorScheme = colorScheme,
                        onClick = {
                            navController.navigate(NavigationDestination.ShiftingEdit.name)
                        }
                    )

                    EditOptionButton(
                        text = "Edit Fitting Charges",
                        icon = Icons.Default.Edit,
                        colorScheme = colorScheme,
                        onClick = {
                            navController.navigate(NavigationDestination.FittingEdit.name)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EditOptionButton(
    text: String,
    icon: ImageVector,
    colorScheme: ColorSchemeModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(16.dp)),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.compColor.copy(alpha = 0.4f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colorScheme.compColor
                )

                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = colorScheme.compColor
                )
            }
        }
    }
}