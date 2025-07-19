package com.eid.onstand.core.utils

import androidx.compose.ui.text.font.FontFamily as ComposeFontFamily
import com.eid.onstand.core.models.FontFamily as AppFontFamily

/**
 * Maps application font family enum to Compose font family
 */
fun AppFontFamily.toComposeFontFamily(): ComposeFontFamily {
    return when (this) {
        AppFontFamily.ROBOTO -> ComposeFontFamily.Default
        AppFontFamily.SANS_SERIF -> ComposeFontFamily.SansSerif
        AppFontFamily.SERIF -> ComposeFontFamily.Serif
        AppFontFamily.MONOSPACE -> ComposeFontFamily.Monospace
    }
}