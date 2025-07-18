package com.eid.onstand

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.di.appModule
import com.eid.onstand.feature.preview.PreviewScreen
import com.eid.onstand.feature.preview.components.ClockPreview
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

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
    val customizationRepository: CustomizationRepository = koinInject()
    val customizationState by customizationRepository.customizationState.collectAsState()

    var showCustomization by remember { mutableStateOf(false) }



    Box(modifier = Modifier) {

        Text(
            text = "Hello, World!",
            modifier = Modifier.hazeEffect(
                style = HazeMaterials.ultraThin()
            ).align(Alignment.Center)
        )
    }


       // Load saved customization state on app start
        LaunchedEffect(Unit) {
            customizationRepository.loadCustomizationState()
        }

        if (showCustomization) {
            PreviewScreen(
                onBackPressed = { showCustomization = false }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                // Display the chosen background and clock
                ClockPreview(
                    backgroundType = customizationState.selectedBackground,
                    clockType = customizationState.selectedClockType,
                    fontColorOption = customizationState.selectedFontColor,
                    layoutOption = customizationState.selectedLayout,
                    modifier = Modifier.fillMaxSize()
                )

                // Floating Action Button to open customization
                FloatingActionButton(
                    onClick = { showCustomization = true },
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