package com.eid.onstand.feature.customization

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.core.ui.theme.Colors

@Composable
fun FontSelectionRow(
    selectedFont: FontFamily,
    onFontSelected: (FontFamily) -> Unit
) {
    val fontFamilies = listOf(
        FontFamily.ROBOTO,
        FontFamily.SERIF,
        FontFamily.MONOSPACE,
        FontFamily.CURSIVE
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(fontFamilies) { font ->
            val isSelected = font == selectedFont
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(300)
            )
            
            FontPreviewCard(
                font = font,
                isSelected = isSelected,
                onSelected = { onFontSelected(font) },
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@Composable
private fun FontPreviewCard(
    font: FontFamily,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(100.dp)
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
        colors = CardDefaults.cardColors(
            containerColor = Colors.BackgroundCard
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Aa",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Colors.TextPrimary else Colors.TextSecondary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = font.displayName,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Colors.TextPrimary else Colors.TextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
            
            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(16.dp)
                        .background(
                            color = Colors.SelectionBorder,
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Colors.TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ColorSelectionRow(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Colors.ClockWhite,
        Colors.ClockWarm,
        Colors.ClockBlush,
        Colors.ClockSage,
        Colors.ClockLavender,
        Colors.ClockDusk,
        Colors.ClockAmber,
        Colors.ClockMist,
        Colors.ClockRose
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(colors) { color ->
            val isSelected = color == selectedColor
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(300)
            )
            
            ColorPreviewCard(
                color = color,
                isSelected = isSelected,
                onSelected = { onColorSelected(color) },
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@Composable
private fun ColorPreviewCard(
    color: Color,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(60.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.SelectionBorder,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Colors.Transparent
        ),
        shape = androidx.compose.foundation.shape.CircleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = color,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        ) {
            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp)
                        .background(
                            color = Colors.SelectionBorder,
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 12.sp,
                        color = Colors.TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}