package com.eid.onstand.feature.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.feature.preview.components.*
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(
    onBackPressed: (() -> Unit)? = null,
    viewModel: PreviewViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val customizationState by viewModel.customizationState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF2C2C2C),
                        Color(0xFF1A1A1A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            TopAppBar(
                title = {
                    Text(
                        text = "Customize",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    TextButton(
                        onClick = {
                            onBackPressed?.invoke() ?: viewModel.toggleCustomizationMode()
                        }
                    ) {
                        Text(
                            text = "←",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Clock Preview
                ClockPreview(
                    backgroundOption = customizationState.selectedBackground,
                    clockStyle = customizationState.selectedClockStyle,
                    fontColorOption = customizationState.selectedFontColor,
                    layoutOption = customizationState.selectedLayout,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Background Selection
                BackgroundSelector(
                    backgroundOptions = uiState.backgroundOptions,
                    selectedBackground = customizationState.selectedBackground,
                    onBackgroundSelected = viewModel::selectBackground,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Clock Style and Font Color Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Clock Style
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "🕐",
                                    fontSize = 20.sp,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Clock Style",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = customizationState.selectedClockStyle?.name ?: "Digital",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    // Font & Color
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "↑",
                                    fontSize = 20.sp,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Font & Color",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = customizationState.selectedFontColor?.name ?: "Modern",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                // Clock Style Selection
                if (uiState.clockStyles.isNotEmpty()) {
                    ClockStyleSelector(
                        clockStyles = uiState.clockStyles,
                        selectedClockStyle = customizationState.selectedClockStyle,
                        onClockStyleSelected = viewModel::selectClockStyle,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                }

                // Font Color Selection
                if (uiState.fontColorOptions.isNotEmpty()) {
                    FontColorSelector(
                        fontColorOptions = uiState.fontColorOptions,
                        selectedFontColor = customizationState.selectedFontColor,
                        onFontColorSelected = viewModel::selectFontColor,
                        modifier = Modifier.padding(bottom = 100.dp)
                    )
                }
            }
        }

        // Bottom Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cancel Button
            OutlinedButton(
                onClick = {
                    viewModel.cancelCustomization()
                    onBackPressed?.invoke()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // Apply Button
            Button(
                onClick = {
                    viewModel.applyCustomization()
                    onBackPressed?.invoke()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7B68EE)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Apply",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PreviewScreenContent() {
    PreviewScreen()
}