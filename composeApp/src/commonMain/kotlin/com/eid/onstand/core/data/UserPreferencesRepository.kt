package com.eid.onstand.core.data

import com.eid.onstand.core.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPreferencesRepository(
    private val backgroundRepository: BackgroundRepository,
    private val clockRepository: ClockRepository
) {
    private val _savedCustomizationState = MutableStateFlow<CustomizationState?>(null)
    val savedCustomizationState: StateFlow<CustomizationState?> = _savedCustomizationState

    suspend fun saveCustomizationState(state: CustomizationState) {
        _savedCustomizationState.value = state
        // Here we would normally persist to DataStore, but for now just keep in memory
    }

    suspend fun loadCustomizationState(): CustomizationState? {
        return _savedCustomizationState.value
    }

    fun getDefaultCustomizationState(): CustomizationState {
        return CustomizationState(
            selectedBackground = backgroundRepository.getBackgroundTypes().first(),
            selectedClockType = clockRepository.getClockTypes().first(),
            selectedFontColor = clockRepository.getFontColorOptions().first(),
            selectedLayout = clockRepository.getLayoutOptions().first()
        )
    }

    suspend fun clearCustomizationState() {
        _savedCustomizationState.value = null
    }
}