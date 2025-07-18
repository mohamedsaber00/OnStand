package com.eid.onstand.feature.preview.utils

import com.eid.onstand.core.models.AbstractPatternType
import com.eid.onstand.core.models.BackgroundOption
import com.eid.onstand.core.models.BackgroundType
import com.eid.onstand.core.models.PatternType

// Helper function to convert BackgroundType to BackgroundOption for backward compatibility
fun convertBackgroundTypeToOption(backgroundType: BackgroundType?): BackgroundOption? {
    return when (backgroundType) {
        is BackgroundType.Solid -> BackgroundOption.SolidColor(
            name = backgroundType.name,
            previewColor = backgroundType.previewColor,
            color = backgroundType.color
        )

        is BackgroundType.Gradient -> BackgroundOption.Gradient(
            name = backgroundType.name,
            previewColor = backgroundType.previewColor,
            colors = backgroundType.colors,
            angle = 0f
        )

        is BackgroundType.Shader -> BackgroundOption.Shader(
            name = backgroundType.name,
            previewColor = backgroundType.previewColor,
            shaderType = backgroundType.shaderType
        )

        is BackgroundType.Live -> BackgroundOption.Live(
            name = backgroundType.name,
            previewColor = backgroundType.previewColor,
            animationType = backgroundType.animationType
        )

        is BackgroundType.Pattern -> BackgroundOption.Abstract(
            name = backgroundType.name,
            previewColor = backgroundType.previewColor,
            patternType = when (backgroundType.patternType) {
                PatternType.GEOMETRIC -> AbstractPatternType.GEOMETRIC
                PatternType.ORGANIC -> AbstractPatternType.ORGANIC
                PatternType.MINIMAL -> AbstractPatternType.MINIMAL
                PatternType.ARTISTIC -> AbstractPatternType.ARTISTIC
            }
        )

        null -> null
    }
}