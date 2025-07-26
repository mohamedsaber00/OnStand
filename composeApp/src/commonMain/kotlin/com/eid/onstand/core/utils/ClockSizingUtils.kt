package com.eid.onstand.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

/**
 * Utilities for consistent clock sizing across different widgets and screen sizes
 */
object ClockSizingUtils {

    /**
     * Calculate optimal time font size based on container dimensions
     */
    @Composable
    fun calculateTimeFontSize(
        containerWidth: Dp,
        containerHeight: Dp,
        characterCount: Int,
        isPreview: Boolean = false
    ): TextUnit {
        val density = LocalDensity.current

        // Calculate based on width (characters should fit comfortably)
        val widthBasedSize = with(density) {
            (containerWidth.toPx() / characterCount * 0.75f).toSp()
        }

        // Calculate based on height (leave room for date/day)
        val heightBasedSize = with(density) {
            (containerHeight.toPx() * 0.45f).toSp()
        }

        // Use the smaller to ensure it fits
        val baseSize = min(widthBasedSize.value, heightBasedSize.value).sp

        // Apply limits
        return when {
            isPreview -> {
                val limited = baseSize.value.coerceIn(12f, 48f)
                limited.sp
            }
            else -> {
                val limited = baseSize.value.coerceIn(24f, 180f)
                limited.sp
            }
        }
    }

    /**
     * Calculate date/day font size as a proportion of time font size
     */
    fun calculateDateFontSize(timeFontSize: TextUnit): TextUnit {
        val size = (timeFontSize.value * 0.25f).coerceAtLeast(12f)
        return size.sp
    }

    /**
     * Calculate adaptive padding based on container size
     */
    @Composable
    fun calculateAdaptivePadding(
        containerWidth: Dp,
        containerHeight: Dp,
        isPreview: Boolean = false
    ): Dp {
        val minDimension = min(containerWidth.value, containerHeight.value)
        val basePadding = (minDimension * 0.08f).dp

        return when {
            isPreview -> {
                val limited = basePadding.value.coerceIn(8f, 16f)
                limited.dp
            }
            else -> {
                val limited = basePadding.value.coerceIn(16f, 48f)
                limited.dp
            }
        }
    }

    /**
     * Calculate spacing between elements
     */
    fun calculateSpacing(containerHeight: Dp, isPreview: Boolean = false): Dp {
        val baseSpacing = (containerHeight.value * 0.05f).dp

        return when {
            isPreview -> {
                val limited = baseSpacing.value.coerceIn(4f, 8f)
                limited.dp
            }
            else -> {
                val limited = baseSpacing.value.coerceIn(8f, 24f)
                limited.dp
            }
        }
    }

    /**
     * Get size category based on container dimensions
     */
    fun getSizeCategory(containerWidth: Dp, containerHeight: Dp): SizeCategory {
        val minDimension = min(containerWidth.value, containerHeight.value)

        return when {
            minDimension < 360 -> SizeCategory.COMPACT
            minDimension < 600 -> SizeCategory.NORMAL
            else -> SizeCategory.EXPANDED
        }
    }

    enum class SizeCategory {
        COMPACT,
        NORMAL,
        EXPANDED
    }

    /**
     * Get responsive scale factor for preview mode
     */
    fun getPreviewScaleFactor(
        availableWidth: Dp,
        availableHeight: Dp,
        targetWidth: Dp = 300.dp,
        targetHeight: Dp = 150.dp
    ): Float {
        val widthScale = availableWidth.value / targetWidth.value
        val heightScale = availableHeight.value / targetHeight.value
        return min(widthScale, heightScale).coerceIn(0.3f, 1.0f)
    }
}