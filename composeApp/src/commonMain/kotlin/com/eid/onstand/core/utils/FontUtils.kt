package com.eid.onstand.core.utils

import androidx.compose.ui.text.font.FontFamily as ComposeFontFamily
import com.eid.onstand.core.models.FontFamily as AppFontFamily

/**
 * Maps application font family enum to Compose font family
 * Each font family maps to a visually distinct Compose font
 */
fun AppFontFamily.toComposeFontFamily(): ComposeFontFamily {
    return when (this) {
        AppFontFamily.ROBOTO -> ComposeFontFamily.Default      // System default (clean, modern)
        AppFontFamily.SERIF -> ComposeFontFamily.Serif         // Serif fonts (with decorative strokes)
        AppFontFamily.MONOSPACE -> ComposeFontFamily.Monospace // Fixed-width fonts (coding style)
        AppFontFamily.CURSIVE -> ComposeFontFamily.Cursive     // Script/handwriting style fonts
    }
}