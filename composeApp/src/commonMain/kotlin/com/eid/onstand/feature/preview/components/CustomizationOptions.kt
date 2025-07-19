package com.eid.onstand.feature.preview.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*
import com.eid.onstand.feature.backgrounds.compose.AnimatedBackground
import com.eid.onstand.feature.backgrounds.compose.FoggyBackground
import com.eid.onstand.feature.backgrounds.compose.RotatingGradientBackground
import com.eid.onstand.feature.backgrounds.shader.EtherShader
import com.eid.onstand.feature.backgrounds.shader.GlowingRing
import com.eid.onstand.feature.backgrounds.shader.MovingTrianglesShader
import com.eid.onstand.feature.backgrounds.shader.MovingWaveShader
import com.eid.onstand.feature.backgrounds.shader.PaletteShader
import com.eid.onstand.feature.backgrounds.shader.PurpleGradientShader
import com.eid.onstand.feature.backgrounds.shader.PurpleSmokeShader
import com.eid.onstand.feature.backgrounds.shader.RedShader
import com.eid.onstand.feature.backgrounds.shader.SpaceShader
import com.mikepenz.hypnoticcanvas.shaderBackground
import kotlinx.coroutines.launch

@Composable
fun BackgroundSelector(
    backgroundOptions: List<BackgroundOption>,
    gradientOptions: List<BackgroundOption>,
    staticColorOptions: List<BackgroundOption>,
    selectedBackground: BackgroundOption?,
    onBackgroundSelected: (BackgroundOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Backgrounds",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        // First row - Main background options (Static, Gradient, Live, Shader)
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(backgroundOptions) { option ->
                val isSelected = when (option.name) {
                    "Static" -> selectedBackground is BackgroundOption.SolidColor
                    "Gradient" -> selectedBackground is BackgroundOption.Gradient && selectedBackground.name != "Gradient"
                    else -> selectedBackground == option
                }

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = tween(300)
                )

                BackgroundOptionItem(
                    option = option,
                    isSelected = isSelected,
                    onSelected = {
                        onBackgroundSelected(option)

                        // Animate to center the selected item
                        coroutineScope.launch {
                            val selectedIndex = backgroundOptions.indexOf(option)
                            listState.animateScrollToItem(
                                index = selectedIndex,
                                scrollOffset = -200 // Adjust to center the item
                            )
                        }
                    },
                    modifier = Modifier.scale(scale)
                )
            }
        }

        // Second row - Show options based on selected category
        val showSecondRow = selectedBackground?.name == "Static" ||
                selectedBackground?.name == "Gradient" ||
                selectedBackground is BackgroundOption.SolidColor ||
                (selectedBackground is BackgroundOption.Gradient && selectedBackground.name != "Gradient")

        if (showSecondRow) {
            Spacer(modifier = Modifier.height(12.dp))

            val secondRowOptions = when {
                selectedBackground?.name == "Static" || selectedBackground is BackgroundOption.SolidColor -> staticColorOptions
                selectedBackground?.name == "Gradient" || (selectedBackground is BackgroundOption.Gradient && selectedBackground.name != "Gradient") -> gradientOptions
                else -> emptyList()
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(secondRowOptions) { option ->
                    val isSelected = selectedBackground == option
                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1.0f else 0.9f,
                        animationSpec = tween(300)
                    )

                    StaticColorOptionItem(
                        option = option,
                        isSelected = isSelected,
                        onSelected = { onBackgroundSelected(option) },
                        modifier = Modifier.scale(scale)
                    )
                }
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
    Card(
        modifier = modifier
            .width(120.dp)
            .height(80.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color(0xFF7B68EE),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .then(
                    // Apply actual shader background if it's a shader type
                    when (option) {
                        is BackgroundOption.Shader -> {
                            when (option.shaderType) {
                                ShaderType.ETHER -> Modifier.shaderBackground(EtherShader)
                                ShaderType.GLOWING_RING -> Modifier.shaderBackground(GlowingRing)
                                ShaderType.MOVING_TRIANGLES -> Modifier.shaderBackground(
                                    MovingTrianglesShader
                                )
                                ShaderType.MOVING_WAVES -> Modifier.shaderBackground(
                                    MovingWaveShader
                                )
                                ShaderType.PURPLE_GRADIENT -> Modifier.shaderBackground(
                                    PurpleGradientShader
                                )
                                ShaderType.SPACE -> Modifier.shaderBackground(SpaceShader)
                                ShaderType.PALETTE -> Modifier.shaderBackground(PaletteShader)
                                ShaderType.RED -> Modifier.shaderBackground(RedShader)
                                ShaderType.PURPLE_SMOKE -> Modifier.shaderBackground(
                                    PurpleSmokeShader)
                            }
                        }

                        else -> Modifier.background(getBackgroundBrush(option))
                    }
                )
        ) {
            // Render animated backgrounds (non-shader)
            when (option) {
                is BackgroundOption.Live -> {
                    when (option.animationType) {
                        LiveAnimationType.ROTATING_GRADIENT -> {
                            RotatingGradientBackground()
                        }

                        LiveAnimationType.FOG_EFFECT -> {
                            FoggyBackground(modifier = Modifier.fillMaxSize())
                        }

                        LiveAnimationType.ANIMATED_PARTICLES -> {
                            AnimatedBackground(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {}
                    }
                }

                else -> {}
            }

            // Overlay with name
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
            )

            Text(
                text = option.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )

            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                        .background(
                            color = Color(0xFF7B68EE),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StaticColorOptionItem(
    option: BackgroundOption,
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
                        color = Color(0xFF7B68EE),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = androidx.compose.foundation.shape.CircleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = getBackgroundBrush(option),
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
                            color = Color(0xFF7B68EE),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ClockStyleSelector(
    clockTypes: List<ClockType>,
    selectedClockType: ClockType?,
    selectedFontColor: FontColorOption?,
    onClockTypeSelected: (ClockType) -> Unit,
    onSecondsToggled: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Clock Styles",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(clockTypes) { type ->
                val isSelected = selectedClockType == type
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = tween(300)
                )

                ClockStyleItem(
                    clockType = type,
                    fontColorOption = selectedFontColor ?: getFallbackFontColor(),
                    isSelected = isSelected,
                    onSelected = {
                        onClockTypeSelected(type)
                        // Animate to center the selected item
                        coroutineScope.launch {
                            val selectedIndex = clockTypes.indexOf(type)
                            listState.animateScrollToItem(
                                index = selectedIndex,
                                scrollOffset = -200
                            )
                        }
                    },
                    modifier = Modifier.scale(scale)
                )
            }
        }

        // Show seconds toggle for digital clocks that support it
        val canShowSeconds = selectedClockType?.isDigital == true

        if (canShowSeconds) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show Seconds",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                Switch(
                    checked = selectedClockType?.showSeconds ?: false,
                    onCheckedChange = onSecondsToggled,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF7B68EE),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color(0xFF4A4A4A)
                    )
                )
            }
        }
    }
}

@Composable
private fun ClockStyleItem(
    clockType: ClockType,
    fontColorOption: FontColorOption,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .height(100.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color(0xFF7B68EE),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show actual clock face preview
            ClockFaceItemPreview(
                clockType = clockType,
                fontColorOption = fontColorOption,
                isSelected = isSelected
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = clockType.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
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
                            color = Color(0xFF7B68EE),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
        Text(
            text = "Font Colors",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(fontColorOptions) { option ->
                val isSelected = selectedFontColor == option
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = tween(300)
                )

                FontColorItem(
                    option = option,
                    isSelected = isSelected,
                    onSelected = {
                        onFontColorSelected(option)
                        // Animate to center the selected item
                        coroutineScope.launch {
                            val selectedIndex = fontColorOptions.indexOf(option)
                            listState.animateScrollToItem(
                                index = selectedIndex,
                                scrollOffset = -200
                            )
                        }
                    },
                    modifier = Modifier.scale(scale)
                )
            }
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
            .width(100.dp)
            .height(80.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color(0xFF7B68EE),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(option.primaryColor)
                )

                if (option.secondaryColor != null) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(option.secondaryColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = option.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
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
                            color = Color(0xFF7B68EE),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun getFallbackFontColor(): FontColorOption {
    return FontColorOption(
        name = "White",
        primaryColor = Color.White,
        secondaryColor = Color.White.copy(alpha = 0.7f),
        style = FontStyle.MODERN
    )
}

@Composable
private fun getBackgroundBrush(option: BackgroundOption): Brush {
    return when (option) {
        is BackgroundOption.Gradient -> {
            if (option.colors.isNotEmpty()) {
                Brush.linearGradient(option.colors)
            } else {
                Brush.linearGradient(
                    listOf(option.previewColor, option.previewColor)
                )
            }
        }
        is BackgroundOption.SolidColor -> {
            Brush.linearGradient(
                listOf(option.color, option.color)
            )
        }
        is BackgroundOption.Abstract -> {
            Brush.linearGradient(
                listOf(option.previewColor, option.previewColor)
            )
        }
        is BackgroundOption.Live -> {
            Brush.linearGradient(
                listOf(option.previewColor, option.previewColor)
            )
        }
        is BackgroundOption.Shader -> {
            Brush.linearGradient(
                listOf(option.previewColor, option.previewColor)
            )
        }
    }
}