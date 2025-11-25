package com.eid.onstand.feature.customization

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.ui.theme.GradientColors
import org.koin.compose.viewmodel.koinViewModel

/**
 * Simple customization screen for selecting backgrounds.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationScreen(
    onBackPressed: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: CustomizationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.linearGradient(GradientColors.SCREEN_BACKGROUND))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            TopAppBar(
                title = {
                    Text(
                        text = "Customize",
                        color = Colors.TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBackPressed) {
                        Text("<-", color = Colors.TextPrimary, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Colors.Transparent
                )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Preview at the top
                PreviewCard(
                    background = uiState.selectedBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .padding(bottom = 24.dp)
                )

                // Background selection
                Text(
                    text = "Backgrounds",
                    color = Colors.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                BackgroundSelectionRow(
                    backgrounds = uiState.backgrounds,
                    selectedBackground = uiState.selectedBackground,
                    onBackgroundSelected = viewModel::selectBackground
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Bottom Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Colors.Black.copy(alpha = 0.5f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackPressed,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Colors.TextPrimary,
                    containerColor = Colors.ButtonBackground
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

            Button(
                onClick = { viewModel.applySettings { onBackPressed() } },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.ButtonPrimaryTransparent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Apply",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Colors.TextPrimary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun PreviewCard(
    background: BackgroundEffect?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Colors.BackgroundCardTransparent
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            background?.Render(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun BackgroundSelectionRow(
    backgrounds: List<BackgroundEffect>,
    selectedBackground: BackgroundEffect?,
    onBackgroundSelected: (BackgroundEffect) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(backgrounds) { background ->
            val isSelected = background == selectedBackground
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(300)
            )

            BackgroundPreviewCard(
                background = background,
                isSelected = isSelected,
                onSelected = { onBackgroundSelected(background) },
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@Composable
private fun BackgroundPreviewCard(
    background: BackgroundEffect,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .height(80.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.SelectionBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(containerColor = Colors.Transparent),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            background.Render(modifier = Modifier.fillMaxSize())

            // Overlay with name
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(GradientColors.BACKGROUND_OVERLAY))
            )

            Text(
                text = background.displayName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Colors.TextPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                        .background(
                            color = Colors.SelectionIndicator,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "v",
                        fontSize = 12.sp,
                        color = Colors.TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
