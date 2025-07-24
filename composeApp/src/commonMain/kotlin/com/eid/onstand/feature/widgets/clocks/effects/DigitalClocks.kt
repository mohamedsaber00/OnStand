package com.eid.onstand.feature.widgets.clocks.effects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.DigitalClockWidget
import com.eid.onstand.core.models.FontFamily
import com.eid.onstand.feature.widgets.clocks.BasicClockWidget
import com.eid.onstand.feature.widgets.clocks.DigitalSegmentClock
import com.eid.onstand.feature.widgets.clocks.MorphFlipClockWidget
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
            showSeconds = showSeconds,
            fontFamily = fontFamily,
            textColor = textColor,
            isPreview = isPreview,
            hazeState = hazeState
        )
    }
}

class SegmentDisplayClock : DigitalClockWidget() {
    override val displayName = "Digital Segments"
    
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
        DigitalSegmentClock(
            modifier = modifier,
            currentTime = currentTime,
            activeColor = textColor,
            inactiveColor = Color.Transparent,
            showSeconds = showSeconds,
            isPreview = isPreview
        )
    }
}

class FlipClock : DigitalClockWidget() {
    override val displayName = "Morph Flip"
    override val supportsSeconds = false // Flip clocks typically don't show seconds
    
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
        MorphFlipClockWidget(
            modifier = modifier,
            currentTime = currentTime,
            cardColor = Color(0xFFFFA77A).copy(alpha = 0.85f),
            textColor = textColor,
            fontFamily = fontFamily,
            isPreview = isPreview
        )
    }
}