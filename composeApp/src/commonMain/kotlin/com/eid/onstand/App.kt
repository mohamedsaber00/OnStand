package com.eid.onstand

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.eid.onstand.core.di.appModule
import com.eid.onstand.core.di.getPlatformModule
import com.eid.onstand.feature.customization.CustomizationScreen
import com.eid.onstand.feature.dashboard.DashboardScreen
import com.eid.onstand.feature.home.HomeScreen
import com.eid.onstand.navigation.Route
import com.eid.onstand.navigation.navConfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

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
    val backStack = rememberNavBackStack(navConfig, Route.Home)

    fun navigate(route: Route) {
        when (route) {
            is Route.Back -> {
                if (backStack.size > 1) {
                    backStack.removeLastOrNull()
                }
            }
            is Route.Home -> {
                backStack.removeAll { it != Route.Home }
            }
            else -> {
                if (!backStack.contains(route)) {
                    backStack.add(route)
                }
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { navigate(Route.Back) },
        entryProvider = entryProvider {
            entry<Route.Home> {
                Box(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { navigate(Route.Customization) },
                        onNavigateToDashboard = { navigate(Route.Dashboard) }
                    )
                }
            }

            entry<Route.Customization> {
                CustomizationScreen(
                    onBackPressed = { navigate(Route.Back) }
                )
            }

            entry<Route.Dashboard> {
                DashboardScreen(
                    onBackPressed = { navigate(Route.Back) }
                )
            }
        },
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        }
    )
}
