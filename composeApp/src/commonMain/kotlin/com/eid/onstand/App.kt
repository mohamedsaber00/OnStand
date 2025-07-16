package com.eid.onstand

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.di.appModule
import com.eid.onstand.feature.backgrounds.shader.SpaceShader
import com.eid.onstand.feature.preview.PreviewScreen
import com.eid.onstand.feature.widgets.clocks.DigitalSegmentClock
import com.mikepenz.hypnoticcanvas.shaderBackground
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.coroutines.delay

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        MaterialTheme {
            var showCustomization by remember { mutableStateOf(false) }

            if (showCustomization) {
                PreviewScreen(
                    onBackPressed = { showCustomization = false }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shaderBackground(SpaceShader)
                    )

                    var currentTime by remember {
                        mutableStateOf(
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        )
                    }
                    LaunchedEffect(Unit) {
                        while (true) {
                            currentTime =
                                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            delay(1000)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.Black),
                    ) {
                        DigitalSegmentClock(
                            modifier = Modifier.align(Alignment.Center),
                            currentTime = currentTime
                        )
                    }

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
    }
}