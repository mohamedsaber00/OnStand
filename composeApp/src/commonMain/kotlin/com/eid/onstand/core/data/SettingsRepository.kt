package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.eid.onstand.core.models.*
import com.eid.onstand.core.ui.theme.Colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataSource: CustomizationDataSource
) {
    // Default values for first run
    companion object {
        val DEFAULT_BACKGROUND = "SunsetGradient" // First gradient background
        val DEFAULT_CLOCK = "BasicDigitalClock" // First digital clock
        val DEFAULT_FONT = FontFamily.ROBOTO
        val DEFAULT_COLOR = Colors.ClockWhite // Soft white color
    }
    
    fun getSettings(): Flow<CustomizationSettings> {
        return dataSource.getCustomizationState().map { state ->
            if (state == null) {
                // Return defaults on first run
                CustomizationSettings(
                    backgroundId = DEFAULT_BACKGROUND,
                    clockId = DEFAULT_CLOCK,
                    fontFamily = DEFAULT_FONT,
                    textColor = DEFAULT_COLOR
                )
            } else {
                // Map saved state to settings
                CustomizationSettings(
                    backgroundId = state.backgroundId ?: DEFAULT_BACKGROUND,
                    clockId = state.clockTypeId ?: DEFAULT_CLOCK,
                    fontFamily = state.selectedFont?.let { fontName ->
                        FontFamily.values().find { it.systemName == fontName }
                    } ?: DEFAULT_FONT,
                    textColor = state.selectedColorName?.let { colorHex ->
                        try {
                            Color(colorHex.toInt())
                        } catch (e: Exception) {
                            DEFAULT_COLOR
                        }
                    } ?: DEFAULT_COLOR
                )
            }
        }
    }
    
    suspend fun saveSettings(
        background: BackgroundEffect?,
        clock: ClockWidget?,
        font: FontFamily,
        color: Color
    ) {
        val state = SerializableCustomizationState(
            backgroundId = background?.typeId,
            backgroundType = background?.category?.name,
            clockTypeId = clock?.typeId,
            clockTypeName = clock?.displayName,
            selectedFont = font.systemName,
            selectedColorName = color.toArgb().toString()
        )
        dataSource.saveCustomizationState(state)
    }
    
    suspend fun clearSettings() {
        dataSource.clearCustomizationState()
    }
}

data class CustomizationSettings(
    val backgroundId: String,
    val clockId: String,
    val fontFamily: FontFamily,
    val textColor: Color
)