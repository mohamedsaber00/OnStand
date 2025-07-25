package com.eid.onstand.feature.customization

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.ui.theme.GradientColors
import com.eid.onstand.core.data.SettingsRepository
import org.koin.compose.koinInject
import kotlinx.coroutines.launch
import dev.chrisbanes.haze.rememberHazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import kotlinx.coroutines.delay
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Customization screen for selecting backgrounds, clocks, fonts, and colors.
 * Provides horizontal scrolling interface with live previews.
 */
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalTime::class,
    ExperimentalHazeMaterialsApi::class
)
@Composable
fun CustomizationScreen(
    selectedBackground: BackgroundEffect? = null,
    selectedClock: ClockWidget? = null,
    selectedFont: FontFamily = FontFamily.ROBOTO,
    selectedColor: Color = Color.White,
    onBackgroundSelected: (BackgroundEffect) -> Unit = {},
    onClockSelected: (ClockWidget) -> Unit = {},
    onFontSelected: (FontFamily) -> Unit = {},
    onColorSelected: (Color) -> Unit = {},
    onBackPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val settingsRepository: SettingsRepository = koinInject()
    val coroutineScope = rememberCoroutineScope()
    var currentTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    val hazeState = rememberHazeState()

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            delay(1000)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(GradientColors.SCREEN_BACKGROUND)
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
                        color = Colors.TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBackPressed) {
                        Text("←", color = Colors.TextPrimary, fontSize = 24.sp)
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
                // Preview at the top - fixed height for consistency
                PreviewCard(
                    background = selectedBackground,
                    clock = selectedClock,
                    currentTime = currentTime,
                    fontFamily = selectedFont,
                    textColor = selectedColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 24.dp)
                )

                // Scrollable content - remaining space
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .haze(hazeState),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    // All Backgrounds in one row
                    Text(
                        text = "Backgrounds",
                        color = Colors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    BackgroundSelectionRow(
                        backgrounds = BackgroundRegistry.getAll(),
                        selectedBackground = selectedBackground,
                        onBackgroundSelected = onBackgroundSelected
                    )

                    // Clock Selection from Registry
                    Text(
                        text = "Clock Styles",
                        color = Colors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ClockSelectionRow(
                        clocks = ClockRegistry.getAll(),
                        selectedClock = selectedClock,
                        onClockSelected = onClockSelected
                    )

                    // Font Selection - only show for digital clocks
                    if (selectedClock?.isDigital == true) {
                        Text(
                            text = "Fonts",
                            color = Colors.TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        FontSelectionRow(
                            selectedFont = selectedFont,
                            onFontSelected = onFontSelected
                        )
                    }

                    // Color Selection
                    Text(
                        text = "Colors",
                        color = Colors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    ColorSelectionRow(
                        selectedColor = selectedColor,
                        onColorSelected = onColorSelected,
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
                .hazeEffect(hazeState, style = HazeMaterials.regular(containerColor = Colors.Black))
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cancel Button
            OutlinedButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .weight(1f),
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

            // Apply Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        settingsRepository.saveSettings(
                            background = selectedBackground,
                            clock = selectedClock,
                            font = selectedFont,
                            color = selectedColor
                        )
                        onBackPressed()
                    }
                },
                modifier = Modifier
                    .weight(1f),
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
    clock: ClockWidget?,
    currentTime: kotlinx.datetime.LocalDateTime,
    fontFamily: FontFamily = FontFamily.ROBOTO,
    textColor: Color = Color.White,
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
            // Render selected background
            background?.Render(modifier = Modifier.fillMaxSize())

            // Render selected clock with proper preview sizing
            clock?.Render(
                currentTime = currentTime,
                showSeconds = true,
                fontFamily = fontFamily,
                textColor = textColor,
                isPreview = true,
                hazeState = null, // No haze in preview for better visibility
                modifier = Modifier.fillMaxSize()
            )
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
        colors = CardDefaults.cardColors(
            containerColor = Colors.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            // Background preview
            background.Render(modifier = Modifier.fillMaxSize())

            // Overlay with name
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(GradientColors.BACKGROUND_OVERLAY)
                    )
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

            // Selection indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                        .background(
                            color = Colors.SelectionIndicator,
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

@Composable
private fun ClockSelectionRow(
    clocks: List<ClockWidget>,
    selectedClock: ClockWidget?,
    onClockSelected: (ClockWidget) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(clocks) { clock ->
            val isSelected = clock == selectedClock
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1f,
                animationSpec = tween(300)
            )

            ClockPreviewCard(
                clock = clock,
                isSelected = isSelected,
                onSelected = { onClockSelected(clock) },
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun ClockPreviewCard(
    clock: ClockWidget,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(110.dp)
            .height(90.dp)
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
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show actual clock preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                // Mini clock preview
                var previewTime by remember {
                    mutableStateOf(
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                }

                LaunchedEffect(Unit) {
                    while (true) {
                        previewTime =
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        delay(1000)
                    }
                }

                // Simplified preview without container for small cards
                clock.Render(
                    currentTime = previewTime,
                    showSeconds = false,
                    fontFamily = FontFamily.ROBOTO,
                    textColor = Colors.ClockDefault,
                    isPreview = true,
                    hazeState = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = clock.displayName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Colors.TextPrimary else Colors.TextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
