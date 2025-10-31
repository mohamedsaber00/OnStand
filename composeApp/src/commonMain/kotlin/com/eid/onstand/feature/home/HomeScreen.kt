package com.eid.onstand.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.*
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.rememberHazeState
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

/**
 * HomeScreen that displays the selected background and clock.
 * Shows the main view with background effect and clock widget.
 */
@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalTime::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNavigateToDashboard: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    
    val hazeState = rememberHazeState()

    // Show loading or empty state while data is loading
    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize())
        return
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
            val backgroundToRender = uiState.selectedBackground
                ?: BackgroundRegistry.getAll().firstOrNull()

            backgroundToRender?.Render(modifier = Modifier.fillMaxSize())
        }

        // Clock layer - render the selected clock widget
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Use default clock if none selected
            val clockToRender = uiState.selectedClock
                ?: ClockRegistry.getAll().firstOrNull()

            clockToRender?.Render(
                currentTime = currentTime,
                showSeconds = true,
                fontFamily = uiState.selectedFont,
                textColor = uiState.selectedColor,
                isPreview = false,
                hazeState = hazeState,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Floating action button to navigate to dashboard
        FloatingActionButton(
            onClick = onNavigateToDashboard,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(
                text = "📊",
                fontSize = 24.sp
            )
        }
    }
}