package com.eid.onstand

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.di.appModule
import com.eid.onstand.feature.preview.PreviewScreen
import com.eid.onstand.feature.preview.components.BackgroundClockView
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
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
            BackgroundClockView(
                backgroundType = customizationState.selectedBackground,
                clockType = customizationState.selectedClockType,
                fontColorOption = customizationState.selectedFontColor,
                layoutOption = customizationState.selectedLayout,
                modifier = Modifier.fillMaxSize()
            )

            // Floating Action Button to open customization
            FloatingActionButton(
                onClick = { viewModel.showCustomization() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFF7B68EE),
                shape = CircleShape
            ) {
                Text(
                    text = "⚙️",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}