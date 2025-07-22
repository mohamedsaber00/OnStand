package com.eid.onstand

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.eid.onstand.core.di.appModule
import com.eid.onstand.core.di.getPlatformModule
import com.eid.onstand.feature.preview.PreviewScreen
import com.eid.onstand.feature.preview.components.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule, getPlatformModule())
        }
    ) {
        MaterialTheme {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val viewModel: AppViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val customizationState by viewModel.customizationState.collectAsState()

    if (uiState.showCustomization) {
        PreviewScreen(
            onBackPressed = { viewModel.hideCustomization() }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            // Display the chosen background and clock
            HomeScreen(
                backgroundType = customizationState.selectedBackground,
                clockType = customizationState.selectedClockType,
                fontFamily = customizationState.selectedFont,
                clockColor = customizationState.selectedColor,
                modifier = Modifier.fillMaxSize().clickable {
                    viewModel.showCustomization()
                }
            )
        }
    }
}