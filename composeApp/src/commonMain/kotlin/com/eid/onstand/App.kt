package com.eid.onstand

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.eid.onstand.core.AppInitializer
import com.eid.onstand.core.di.appModule
import com.eid.onstand.core.di.getPlatformModule
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.BackgroundRegistry
import com.eid.onstand.core.models.ClockRegistry
import com.eid.onstand.core.models.ClockWidget
import com.eid.onstand.feature.home.HomeScreen
import com.eid.onstand.feature.customization.CustomizationScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule, getPlatformModule())
            // Initialize the app modules after Koin is set up
            AppInitializer.initialize()
        }
    ) {
        MaterialTheme {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    var showCustomization by remember { mutableStateOf(false) }
    var selectedBackground by remember { mutableStateOf<BackgroundEffect?>(null) }
    var selectedClock by remember { mutableStateOf<ClockWidget?>(null) }
    
    // Initialize with defaults from registry
    LaunchedEffect(Unit) {
        if (selectedBackground == null) {
            selectedBackground = BackgroundRegistry.getAll().firstOrNull()
        }
        if (selectedClock == null) {
            selectedClock = ClockRegistry.getAll().firstOrNull()
        }
    }

    if (showCustomization) {
        CustomizationScreen(
            selectedBackground = selectedBackground,
            selectedClock = selectedClock,
            onBackgroundSelected = { background ->
                selectedBackground = background
            },
            onClockSelected = { clock ->
                selectedClock = clock
            },
            onBackPressed = { showCustomization = false }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                selectedBackground = selectedBackground,
                selectedClock = selectedClock,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showCustomization = true }
            )
        }
    }
}