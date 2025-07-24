package com.eid.onstand.feature.preview.components

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
 * HomeScreen that uses the new registry system directly.
 * This shows how to render backgrounds and clocks from the registries.
 */
@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalTime::class)
@Composable
fun HomeScreenWithRegistry(
    selectedBackground: BackgroundEffect? = null,
    selectedClock: ClockWidget? = null,
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
                fontFamily = FontFamily.ROBOTO,
                textColor = Color.White,
                isPreview = false,
                hazeState = hazeState,
                modifier = Modifier
            )
        }
    }
}

/**
 * Example of how to use the registry in a ViewModel or state management
 */
class RegistryBasedCustomizationState {
    var selectedBackground by mutableStateOf<BackgroundEffect?>(null)
    var selectedClock by mutableStateOf<ClockWidget?>(null)
    
    fun selectBackground(background: BackgroundEffect) {
        selectedBackground = background
    }
    
    fun selectClock(clock: ClockWidget) {
        selectedClock = clock
    }
    
    fun getAllBackgrounds(): List<BackgroundEffect> {
        return BackgroundRegistry.getAll()
    }
    
    fun getAllClocks(): List<ClockWidget> {
        return ClockRegistry.getAll()
    }
    
    fun getBackgroundsByCategory(): Map<BackgroundCategory, List<BackgroundEffect>> {
        return BackgroundRegistry.getByCategory()
    }
    
    // Type-safe access to specific backgrounds
    fun getSpaceBackground(): BackgroundEffect? {
        return BackgroundRegistry.getByTypeId("SpaceBackground")
    }
    
    fun getBasicDigitalClock(): ClockWidget? {
        return ClockRegistry.getByTypeId("BasicDigitalClock")
    }
}