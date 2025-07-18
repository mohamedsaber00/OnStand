package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*

class BackgroundRepository {

    fun getBackgroundTypes(): List<BackgroundType> = listOf(
        // Shader Backgrounds
        BackgroundType.Shader(
            name = "Ether",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.ETHER
        ),
        BackgroundType.Shader(
            name = "Space",
            previewColor = Color(0xFF1A0033),
            shaderType = ShaderType.SPACE
        ),
        BackgroundType.Shader(
            name = "Glowing Ring",
            previewColor = Color(0xFF7B68EE),
            shaderType = ShaderType.GLOWING_RING
        ),
        BackgroundType.Shader(
            name = "Purple Flow",
            previewColor = Color(0xFF9B59B6),
            shaderType = ShaderType.PURPLE_GRADIENT
        ),
        BackgroundType.Shader(
            name = "Triangles",
            previewColor = Color(0xFF2C3E50),
            shaderType = ShaderType.MOVING_TRIANGLES
        ),
        BackgroundType.Shader(
            name = "Moving Waves",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.MOVING_WAVES
        ),
        BackgroundType.Shader(
            name = "Turbulence",
            previewColor = Color(0xFF8E44AD),
            shaderType = ShaderType.TURBULENCE
        ),

        // Live Animated Backgrounds
        BackgroundType.Live(
            name = "Rotating",
            previewColor = Color(0xFF7B68EE),
            animationType = LiveAnimationType.ROTATING_GRADIENT
        ),
        BackgroundType.Live(
            name = "Fog",
            previewColor = Color(0xFF2C3E40),
            animationType = LiveAnimationType.FOG_EFFECT
        ),
        BackgroundType.Live(
            name = "Particles",
            previewColor = Color(0xFF4A90E2),
            animationType = LiveAnimationType.ANIMATED_PARTICLES
        )
    )


    // Legacy support methods - will be removed gradually
    fun getBackgroundOptions(): List<BackgroundOption> = listOf(
        // Shader Backgrounds
        BackgroundOption.Shader(
            name = "Ether",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.ETHER
        ),
        BackgroundOption.Shader(
            name = "Space",
            previewColor = Color(0xFF1A0033),
            shaderType = ShaderType.SPACE
        ),
        BackgroundOption.Shader(
            name = "Glowing Ring",
            previewColor = Color(0xFF7B68EE),
            shaderType = ShaderType.GLOWING_RING
        ),
        BackgroundOption.Shader(
            name = "Purple Flow",
            previewColor = Color(0xFF9B59B6),
            shaderType = ShaderType.PURPLE_GRADIENT
        ),
        BackgroundOption.Shader(
            name = "Triangles",
            previewColor = Color(0xFF2C3E50),
            shaderType = ShaderType.MOVING_TRIANGLES
        ),
        BackgroundOption.Shader(
            name = "Moving Waves",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.MOVING_WAVES
        ),
        BackgroundOption.Shader(
            name = "Turbulence",
            previewColor = Color(0xFF8E44AD),
            shaderType = ShaderType.TURBULENCE
        ),

        // Live Animated Backgrounds
        BackgroundOption.Live(
            name = "Rotating",
            previewColor = Color(0xFF7B68EE),
            animationType = LiveAnimationType.ROTATING_GRADIENT
        ),
        BackgroundOption.Live(
            name = "Fog",
            previewColor = Color(0xFF2C3E40),
            animationType = LiveAnimationType.FOG_EFFECT
        ),
        BackgroundOption.Live(
            name = "Waves",
            previewColor = Color(0xFF4A90E2),
            animationType = LiveAnimationType.ANIMATED_PARTICLES
        ),

        // Static Category (will show solid colors in second row)
        BackgroundOption.SolidColor(
            name = "Static",
            previewColor = Color.Black,
            color = Color.Black
        ),

        // Gradient Category (will show gradient options in second row)
        BackgroundOption.Gradient(
            name = "Gradient",
            previewColor = Color(0xFF4A90E2),
            colors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE)
            )
        ),
    )

    fun getGradientOptions(): List<BackgroundOption> = listOf(
        BackgroundOption.Gradient(
            name = "Sunset",
            previewColor = Color(0xFFFF6B6B),
            colors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE),
                Color(0xFFFF6B6B)
            )
        ),
        BackgroundOption.Gradient(
            name = "Ocean",
            previewColor = Color(0xFF4A90E2),
            colors = listOf(
                Color(0xFF000428),
                Color(0xFF004e92)
            )
        ),
        BackgroundOption.Gradient(
            name = "Forest",
            previewColor = Color(0xFF134E5E),
            colors = listOf(
                Color(0xFF134E5E),
                Color(0xFF71B280)
            )
        ),
        BackgroundOption.Gradient(
            name = "Purple",
            previewColor = Color(0xFF667eea),
            colors = listOf(
                Color(0xFF667eea),
                Color(0xFF764ba2)
            )
        ),
        BackgroundOption.Gradient(
            name = "Fire",
            previewColor = Color(0xFFf12711),
            colors = listOf(
                Color(0xFFf12711),
                Color(0xFFf5af19)
            )
        ),
        BackgroundOption.Gradient(
            name = "Mint",
            previewColor = Color(0xFF00b4db),
            colors = listOf(
                Color(0xFF00b4db),
                Color(0xFF0083b0)
            )
        )
    )

    fun getStaticColorOptions(): List<BackgroundOption> = listOf(
        BackgroundOption.SolidColor(
            name = "Black",
            previewColor = Color.Black,
            color = Color.Black
        ),
        BackgroundOption.SolidColor(
            name = "White",
            previewColor = Color.White,
            color = Color.White
        ),
        BackgroundOption.SolidColor(
            name = "Gray",
            previewColor = Color.Gray,
            color = Color.Gray
        ),
        BackgroundOption.SolidColor(
            name = "Dark Gray",
            previewColor = Color.DarkGray,
            color = Color.DarkGray
        ),
        BackgroundOption.SolidColor(
            name = "Blue",
            previewColor = Color(0xFF2196F3),
            color = Color(0xFF2196F3)
        ),
        BackgroundOption.SolidColor(
            name = "Green",
            previewColor = Color(0xFF4CAF50),
            color = Color(0xFF4CAF50)
        ),
        BackgroundOption.SolidColor(
            name = "Purple",
            previewColor = Color(0xFF9C27B0),
            color = Color(0xFF9C27B0)
        ),
        BackgroundOption.SolidColor(
            name = "Red",
            previewColor = Color(0xFFF44336),
            color = Color(0xFFF44336)
        )
    )
}