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
    var currentScreen by remember { mutableStateOf(Screen.HOME) }

    when (currentScreen) {
        Screen.HOME -> {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { currentScreen = Screen.CUSTOMIZATION },
                    onNavigateToDashboard = { currentScreen = Screen.DASHBOARD }
                )
            }
        }
        Screen.CUSTOMIZATION -> {
            CustomizationScreen(
                onBackPressed = { currentScreen = Screen.HOME }
            )
        }
        Screen.DASHBOARD -> {
            com.eid.onstand.feature.dashboard.DashboardScreen(
                onBackPressed = { currentScreen = Screen.HOME }
            )
        }
    }
}

enum class Screen {
    HOME,
    CUSTOMIZATION,
    DASHBOARD
}