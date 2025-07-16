package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CustomizationRepository(
    private val backgroundRepository: BackgroundRepository,
    private val clockRepository: ClockRepository
) {

    private val _customizationState = MutableStateFlow(CustomizationState())
    val customizationState: StateFlow<CustomizationState> = _customizationState

    fun getBackgroundOptions(): List<BackgroundOption> = backgroundRepository.getBackgroundOptions()
    fun getGradientOptions(): List<BackgroundOption> = backgroundRepository.getGradientOptions()
    fun getStaticColorOptions(): List<BackgroundOption> =
        backgroundRepository.getStaticColorOptions()

    fun getClockStyles(): List<ClockStyle> = clockRepository.getClockStyles()
    fun getFontColorOptions(): List<FontColorOption> = clockRepository.getFontColorOptions()
    fun getLayoutOptions(): List<LayoutOption> = clockRepository.getLayoutOptions()

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