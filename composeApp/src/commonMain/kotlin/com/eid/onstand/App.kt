package com.eid.onstand

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.AppInitializer
import com.eid.onstand.core.di.appModule
import com.eid.onstand.core.di.getPlatformModule
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.BackgroundRegistry
import com.eid.onstand.core.models.ClockRegistry
import com.eid.onstand.core.models.ClockWidget
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.feature.home.HomeScreen
import com.eid.onstand.feature.customization.CustomizationScreen
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.ui.theme.Colors
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import kotlinx.coroutines.flow.first

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
    val settingsRepository: SettingsRepository = koinInject()
    
    var showCustomization by remember { mutableStateOf(false) }
    var selectedBackground by remember { mutableStateOf<BackgroundEffect?>(null) }
    var selectedClock by remember { mutableStateOf<ClockWidget?>(null) }
    var selectedFont by remember { mutableStateOf(FontFamily.ROBOTO) }
    var selectedColor by remember { mutableStateOf(Colors.ClockWhite) }
    var isLoading by remember { mutableStateOf(true) }
    
    // Load saved settings or use defaults
    LaunchedEffect(Unit) {
        try {
            val settings = settingsRepository.getSettings().first()
            
            // Find background by ID
            selectedBackground = BackgroundRegistry.getAll().find { it.typeId == settings.backgroundId }
                ?: BackgroundRegistry.getAll().firstOrNull { it.typeId == SettingsRepository.DEFAULT_BACKGROUND }
                ?: BackgroundRegistry.getAll().firstOrNull()
            
            // Find clock by ID
            selectedClock = ClockRegistry.getAll().find { it.typeId == settings.clockId }
                ?: ClockRegistry.getAll().firstOrNull { it.typeId == SettingsRepository.DEFAULT_CLOCK }
                ?: ClockRegistry.getAll().firstOrNull()
            
            selectedFont = settings.fontFamily
            selectedColor = settings.textColor
        } catch (e: Exception) {
            // Fall back to defaults if loading fails
            selectedBackground = BackgroundRegistry.getAll().firstOrNull()
            selectedClock = ClockRegistry.getAll().firstOrNull()
        } finally {
            isLoading = false
        }
    }

    // Don't show content until settings are loaded
    if (isLoading) {
        // You can add a loading indicator here if desired
        Box(modifier = Modifier.fillMaxSize())
    } else if (showCustomization) {
        CustomizationScreen(
            selectedBackground = selectedBackground,
            selectedClock = selectedClock,
            selectedFont = selectedFont,
            selectedColor = selectedColor,
            onBackgroundSelected = { background ->
                selectedBackground = background
            },
            onClockSelected = { clock ->
                selectedClock = clock
            },
            onFontSelected = { font ->
                selectedFont = font
            },
            onColorSelected = { color ->
                selectedColor = color
            },
            onBackPressed = { showCustomization = false }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                selectedBackground = selectedBackground,
                selectedClock = selectedClock,
                selectedFont = selectedFont,
                selectedColor = selectedColor,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showCustomization = true }
            )
        }
    }
}