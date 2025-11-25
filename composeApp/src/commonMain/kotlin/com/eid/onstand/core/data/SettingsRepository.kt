package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.eid.onstand.core.models.BackgroundEffect
import com.eid.onstand.core.models.Backgrounds
import com.eid.onstand.core.ui.theme.Colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataSource: CustomizationDataSource
) {
    companion object {
        val DEFAULT_BACKGROUND = "EtherBackground"
        val DEFAULT_COLOR = Colors.ClockWhite
    }

    fun getSettings(): Flow<CustomizationSettings> {
        return dataSource.getCustomizationState().map { state ->
            if (state == null) {
                CustomizationSettings(
                    backgroundId = DEFAULT_BACKGROUND,
                    textColor = DEFAULT_COLOR
                )
            } else {
                CustomizationSettings(
                    backgroundId = state.backgroundId ?: DEFAULT_BACKGROUND,
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
        color: Color
    ) {
        val state = SerializableCustomizationState(
            backgroundId = background?.typeId,
            backgroundType = null,
            clockTypeId = null,
            clockTypeName = null,
            selectedFont = null,
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
    val textColor: Color
)
