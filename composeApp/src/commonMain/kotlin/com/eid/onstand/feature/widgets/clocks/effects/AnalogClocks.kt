package com.eid.onstand.feature.widgets.clocks.effects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.AnalogClockWidget
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.feature.widgets.clocks.AnalogClockWidget as AnalogClockWidgetImpl
import dev.chrisbanes.haze.HazeState
import kotlinx.datetime.LocalDateTime

// Type-safe analog clock implementations

class ClassicAnalogClock : AnalogClockWidget() {
    override val displayName = "Analog Classic"
    
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
        AnalogClockWidgetImpl(
            modifier = modifier,
            currentTime = currentTime,
            clockColor = textColor,
            handsColor = textColor,
            numbersColor = textColor.copy(alpha = 0.8f),
            isPreview = isPreview,
            hazeState = hazeState,
        )
    }
}