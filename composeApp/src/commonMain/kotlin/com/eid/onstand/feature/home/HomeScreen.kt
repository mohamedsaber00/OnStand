package com.eid.onstand.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eid.onstand.core.models.*
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.delay
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * HomeScreen that displays the selected background and clock.
 * Shows the main view with background effect and clock widget.
 */
@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalTime::class)
@Composable
fun HomeScreen(
    selectedBackground: BackgroundEffect? = null,
    selectedClock: ClockWidget? = null,
    selectedFont: FontFamily = FontFamily.ROBOTO,
    selectedColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
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
        modifier = modifier.fillMaxSize()
    ) {
        // Background layer - render the selected background effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
        ) {
            // Use default background if none selected
            val backgroundToRender = selectedBackground 
                ?: BackgroundRegistry.getAll().firstOrNull()
            
            backgroundToRender?.Render(modifier = Modifier.fillMaxSize())
        }
        
        // Clock layer - render the selected clock widget
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Use default clock if none selected
            val clockToRender = selectedClock 
                ?: ClockRegistry.getAll().firstOrNull()
            
            clockToRender?.Render(
                currentTime = currentTime,
                showSeconds = true,
                fontFamily = selectedFont,
                textColor = selectedColor,
                isPreview = false,
                hazeState = hazeState,
                modifier = Modifier
            )
        }
    }
}