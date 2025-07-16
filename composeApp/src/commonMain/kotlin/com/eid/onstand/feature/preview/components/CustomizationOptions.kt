package com.eid.onstand.feature.preview.components

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*

@Composable
fun BackgroundSelector(
    backgroundOptions: List<BackgroundOption>,
    selectedBackground: BackgroundOption?,
    onBackgroundSelected: (BackgroundOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Background",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }

            Text(
                text = "→",
                fontSize = 20.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(backgroundOptions) { option ->
                BackgroundOptionItem(
                    option = option,
                    isSelected = selectedBackground?.id == option.id,
                    onSelected = { onBackgroundSelected(option) }
                )
            }
        }
    }
}

@Composable
private fun BackgroundOptionItem(
    option: BackgroundOption,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onSelected() }
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(getBackgroundBrush(option))
                .border(
                    width = if (isSelected) 3.dp else 1.dp,
                    color = if (isSelected) Color(0xFF7B68EE) else Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            if (isSelected) {
                Text(
                    text = "✓",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = option.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ClockStyleSelector(
    clockStyles: List<ClockStyle>,
    selectedClockStyle: ClockStyle?,
    onClockStyleSelected: (ClockStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "🕐",
                fontSize = 24.sp,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Clock Style",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            clockStyles.take(2).forEach { style ->
                ClockStyleItem(
                    style = style,
                    isSelected = selectedClockStyle?.id == style.id,
                    onSelected = { onClockStyleSelected(style) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ClockStyleItem(
    style: ClockStyle,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF4A4A4A) else Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (style.isDigital) "🕐" else "⏰",
                fontSize = 32.sp,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = style.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FontColorSelector(
    fontColorOptions: List<FontColorOption>,
    selectedFontColor: FontColorOption?,
    onFontColorSelected: (FontColorOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FontColorItem(
                option = fontColorOptions.first(),
                isSelected = selectedFontColor?.id == fontColorOptions.first().id,
                onSelected = { onFontColorSelected(fontColorOptions.first()) },
                modifier = Modifier.weight(1f)
            )

            FontColorItem(
                option = fontColorOptions.getOrNull(1) ?: fontColorOptions.first(),
                isSelected = selectedFontColor?.id == fontColorOptions.getOrNull(1)?.id,
                onSelected = {
                    onFontColorSelected(
                        fontColorOptions.getOrNull(1) ?: fontColorOptions.first()
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FontColorItem(
    option: FontColorOption,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF4A4A4A) else Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(option.primaryColor)
                )

                if (option.secondaryColor != null) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(option.secondaryColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = option.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun getBackgroundBrush(option: BackgroundOption): Brush {
    return when (option.type) {
        BackgroundType.GRADIENT -> {
            if (option.gradientColors.isNotEmpty()) {
                Brush.linearGradient(option.gradientColors)
            } else {
                Brush.linearGradient(
                    listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF7B68EE)
                    )
                )
            }
        }
        BackgroundType.SOLID_COLOR -> {
            Brush.linearGradient(
                listOf(
                    option.previewColor ?: Color.Black,
                    option.previewColor ?: Color.Black
                )
            )
        }
        BackgroundType.FOG -> {
            Brush.radialGradient(
                listOf(
                    Color(0xFFE6E6FA),
                    Color(0xFFD3D3D3),
                    Color(0xFFA9A9A9)
                )
            )
        }
        BackgroundType.ABSTRACT -> {
            Brush.linearGradient(
                listOf(
                    Color(0xFFF0F0F0),
                    Color(0xFFE0E0E0)
                )
            )
        }
        BackgroundType.LIVE -> {
            Brush.linearGradient(
                listOf(
                    Color(0xFF87CEEB),
                    Color(0xFF4682B4)
                )
            )
        }
    }
}