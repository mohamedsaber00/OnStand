package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CustomizationRepository {

    private val _customizationState = MutableStateFlow(CustomizationState())
    val customizationState: StateFlow<CustomizationState> = _customizationState

    fun getBackgroundOptions(): List<BackgroundOption> = listOf(
        BackgroundOption(
            id = "gradient_1",
            type = BackgroundType.GRADIENT,
            name = "Gradient",
            gradientColors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE),
                Color(0xFFFF6B6B)
            )
        ),
        BackgroundOption(
            id = "abstract_1",
            type = BackgroundType.ABSTRACT,
            name = "Abstract",
            previewColor = Color(0xFFF0F0F0),
            imageUrl = "abstract_leaf"
        ),
        BackgroundOption(
            id = "live_1",
            type = BackgroundType.LIVE,
            name = "Live",
            previewColor = Color(0xFF87CEEB),
            isLive = true
        ),
        BackgroundOption(
            id = "fog_1",
            type = BackgroundType.FOG,
            name = "Fog",
            previewColor = Color(0xFFE6E6FA)
        ),
        BackgroundOption(
            id = "solid_black",
            type = BackgroundType.SOLID_COLOR,
            name = "Black",
            previewColor = Color.Black
        ),
        BackgroundOption(
            id = "solid_white",
            type = BackgroundType.SOLID_COLOR,
            name = "White",
            previewColor = Color.White
        )
    )

    fun getClockStyles(): List<ClockStyle> = listOf(
        ClockStyle(
            id = "digital_modern",
            name = "Digital",
            fontFamily = "Roboto",
            isDigital = true,
            showSeconds = false,
            showDate = true
        ),
        ClockStyle(
            id = "digital_seconds",
            name = "Digital with Seconds",
            fontFamily = "Roboto",
            isDigital = true,
            showSeconds = true,
            showDate = true
        ),
        ClockStyle(
            id = "analog_classic",
            name = "Analog Classic",
            fontFamily = "Serif",
            isDigital = false,
            showDate = false
        ),
        ClockStyle(
            id = "minimal_digital",
            name = "Minimal",
            fontFamily = "Helvetica",
            isDigital = true,
            showSeconds = false,
            showDate = false
        )
    )

    fun getFontColorOptions(): List<FontColorOption> = listOf(
        FontColorOption(
            id = "modern_white",
            name = "Modern",
            primaryColor = Color.White,
            secondaryColor = Color(0xFFB0B0B0),
            style = FontStyle.MODERN
        ),
        FontColorOption(
            id = "classic_black",
            name = "Classic",
            primaryColor = Color.Black,
            secondaryColor = Color(0xFF666666),
            style = FontStyle.CLASSIC
        ),
        FontColorOption(
            id = "bold_blue",
            name = "Bold Blue",
            primaryColor = Color(0xFF4A90E2),
            secondaryColor = Color(0xFF7B68EE),
            style = FontStyle.BOLD
        ),
        FontColorOption(
            id = "elegant_gold",
            name = "Elegant Gold",
            primaryColor = Color(0xFFFFD700),
            secondaryColor = Color(0xFFFFA500),
            style = FontStyle.ELEGANT
        ),
        FontColorOption(
            id = "green_digital",
            name = "Green Digital",
            primaryColor = Color(0xFF00FF00),
            secondaryColor = Color(0xFF32CD32),
            style = FontStyle.MODERN
        )
    )

    fun getLayoutOptions(): List<LayoutOption> = listOf(
        LayoutOption(
            id = "previous_minutes",
            name = "Previous Minutes",
            showPreviousMinutes = true,
            alignment = TimeAlignment.CENTER
        ),
        LayoutOption(
            id = "clean_center",
            name = "Clean Center",
            showPreviousMinutes = false,
            alignment = TimeAlignment.CENTER
        ),
        LayoutOption(
            id = "left_aligned",
            name = "Left Aligned",
            showPreviousMinutes = true,
            alignment = TimeAlignment.LEFT
        ),
        LayoutOption(
            id = "right_aligned",
            name = "Right Aligned",
            showPreviousMinutes = false,
            alignment = TimeAlignment.RIGHT
        )
    )

    fun updateCustomizationState(newState: CustomizationState) {
        _customizationState.value = newState
    }

    fun selectBackground(background: BackgroundOption) {
        _customizationState.value = _customizationState.value.copy(
            selectedBackground = background
        )
    }

    fun selectClockStyle(clockStyle: ClockStyle) {
        _customizationState.value = _customizationState.value.copy(
            selectedClockStyle = clockStyle
        )
    }

    fun selectFontColor(fontColor: FontColorOption) {
        _customizationState.value = _customizationState.value.copy(
            selectedFontColor = fontColor
        )
    }

    fun selectLayout(layout: LayoutOption) {
        _customizationState.value = _customizationState.value.copy(
            selectedLayout = layout
        )
    }
}