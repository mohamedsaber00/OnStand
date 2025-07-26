package com.eid.onstand.feature.widgets.clocks.effects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.DigitalClockWidget
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.feature.widgets.clocks.BasicClockWidget
import dev.chrisbanes.haze.HazeState
import kotlinx.datetime.LocalDateTime

// Type-safe digital clock implementations

class BasicDigitalClock : DigitalClockWidget() {
    override val displayName = "Digital"
    
    @Composable
    override fun Render(
        currentTime: LocalDateTime,
        showSeconds: Boolean,
        fontFamily: FontFamily,
        textColor: Color,
        isPreview: Boolean,
        hazeState: HazeState?,
        modifier: Modifier
    ) {
        BasicClockWidget(
            modifier = modifier,
            currentTime = currentTime,
            fontFamily = fontFamily,
            textColor = textColor,
            isPreview = isPreview,
            hazeState = hazeState,
        )
    }
}
