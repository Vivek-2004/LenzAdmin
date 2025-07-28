package com.fitting.lenz.screens.details_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitting.lenz.LenzViewModel
import com.fitting.lenz.R
import com.fitting.lenz.models.ColorSchemeModel
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingFullFrame
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingRimless
import com.fitting.lenz.screens.details_screen.fitting_edit_items.FittingSupra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FittingEdit(
    colorScheme: ColorSchemeModel, lenzViewModel: LenzViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pullToRefreshState = rememberPullToRefreshState()

    var isRefreshing by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var updateSuccess by remember { mutableStateOf(false) }

    var fittingUpdateConfirmation by lenzViewModel::fittingUpdateConfirmation

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, title = {
                Text(
                    text = "Confirm Price Update", fontWeight = FontWeight.Bold, fontSize = 18.sp
                )
            }, text = {
                Text(
                    text = "Are you sure you want to update the fitting charges? This action cannot be undone.",
                    fontSize = 15.sp
                )
            }, confirmButton = {
                TextButton(
                    onClick = {
                        isUpdating = true
                        showDialog = false
                    }, colors = ButtonDefaults.textButtonColors(
                        contentColor = colorScheme.compColor
                    )
                ) {
                    Text(
                        text = "Confirm", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                    )
                }
            }, dismissButton = {
                TextButton(
                    onClick = { showDialog = false }, colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray
                    )
                ) {
                    Text(
                        text = "Cancel", fontSize = 16.sp
                    )
                }
            }, containerColor = colorScheme.bgColor, shape = RoundedCornerShape(16.dp)
        )
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) return@LaunchedEffect
        lenzViewModel.getFittingCharges()
        delay(1500L)
        isRefreshing = false
    }

    LaunchedEffect(isUpdating) {
        if (!isUpdating) return@LaunchedEffect
        lenzViewModel.updateFittingCharges()
        delay(1500L)
        isUpdating = false
        if (fittingUpdateConfirmation) {
            updateSuccess = true
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context, "Fitting charges updated successfully", Toast.LENGTH_SHORT
                ).show()
                fittingUpdateConfirmation = false
            }
            delay(3000L)
            updateSuccess = false
        }
    }

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = { isRefreshing = true },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var showFullFrame by remember { mutableStateOf(false) }
            var showSupra by remember { mutableStateOf(false) }
            var showRimless by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Fitting Charges",
                    color = colorScheme.compColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = "Edit Fitting Charges...",
                color = colorScheme.compColor.copy(alpha = 0.7f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            ExpandableCard(
                title = "Full Frame Charges", expanded = showFullFrame, onExpandToggle = {
                    showFullFrame = !showFullFrame
                    if (showFullFrame) {
                        showSupra = false
                        showRimless = false
                    }
                }, colorScheme = colorScheme
            ) {
                if (showFullFrame) {
                    FittingFullFrame(
                        colorScheme = colorScheme, lenzViewModel = lenzViewModel
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableCard(
                title = "Supra Charges", expanded = showSupra, onExpandToggle = {
                    showSupra = !showSupra
                    if (showSupra) {
                        showFullFrame = false
                        showRimless = false
                    }
                }, colorScheme = colorScheme
            ) {
                FittingSupra(
                    colorScheme = colorScheme, lenzViewModel = lenzViewModel
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rimless Section
            ExpandableCard(
                title = "Rimless Charges", expanded = showRimless, onExpandToggle = {
                    showRimless = !showRimless
                    if (showRimless) {
                        showFullFrame = false
                        showSupra = false
                    }
                }, colorScheme = colorScheme
            ) {
                FittingRimless(
                    colorScheme = colorScheme, lenzViewModel = lenzViewModel
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = colorScheme.compColor.copy(alpha = 0.3f)
                    ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = if (isUpdating) colorScheme.compColor.copy(alpha = 0.7f) else colorScheme.compColor
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(28.dp), strokeWidth = 3.dp, color = Color.White
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Updating...",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                        )
                    } else if (updateSuccess) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Updated Successfully",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Update",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Update Fitting Charges",
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Refresh Hint
            Text(
                text = "Pull down to refresh charges",
                color = colorScheme.compColor.copy(alpha = 0.5f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ExpandableCard(
    title: String,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
    colorScheme: ColorSchemeModel,
    content: @Composable () -> Unit
) {
    val transitionState = remember(expanded) {
        MutableTransitionState(expanded).apply {
            targetState = expanded
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) colorScheme.compColor.copy(alpha = 0.1f)
            else Color.Gray.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandToggle() }  // Ensure this is correctly set
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    color = colorScheme.compColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (expanded) colorScheme.compColor.copy(alpha = 0.2f)
                            else Color.Gray.copy(alpha = 0.2f)
                        )
                        .clickable { onExpandToggle() },  // Ensure this is correctly set
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = if (expanded) painterResource(R.drawable.arrow_drop_up) else painterResource(
                            R.drawable.arrow_drop_down
                        ),
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint = if (expanded) colorScheme.compColor else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            AnimatedVisibility(
                visibleState = transitionState,
                enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    content()
                }
            }
        }
    }
}