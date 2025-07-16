package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*

class BackgroundRepository {

    fun getBackgroundOptions(): List<BackgroundOption> = listOf(
        // Static Category (will show solid colors in second row)
        BackgroundOption.SolidColor(
            id = "static_category",
            name = "Static",
            previewColor = Color.Black,
            color = Color.Black
        ),

        // Gradient Category (will show gradient options in second row)
        BackgroundOption.Gradient(
            id = "gradient_category",
            name = "Gradient",
            previewColor = Color(0xFF4A90E2),
            colors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE)
            )
        ),

        // Live Animated Backgrounds
        BackgroundOption.Live(
            id = "live_rotating_gradient",
            name = "Rotating",
            previewColor = Color(0xFF7B68EE),
            animationType = LiveAnimationType.ROTATING_GRADIENT
        ),
        BackgroundOption.Live(
            id = "live_fog",
            name = "Fog",
            previewColor = Color(0xFF2C3E40),
            animationType = LiveAnimationType.FOG_EFFECT
        ),
        BackgroundOption.Live(
            id = "live_animated_waves",
            name = "Waves",
            previewColor = Color(0xFF4A90E2),
            animationType = LiveAnimationType.ANIMATED_PARTICLES
        ),

        // Shader Backgrounds
        BackgroundOption.Shader(
            id = "shader_ether",
            name = "Ether",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.ETHER
        ),
        BackgroundOption.Shader(
            id = "shader_space",
            name = "Space",
            previewColor = Color(0xFF1A0033),
            shaderType = ShaderType.SPACE
        ),
        BackgroundOption.Shader(
            id = "shader_glowing_ring",
            name = "Glowing Ring",
            previewColor = Color(0xFF7B68EE),
            shaderType = ShaderType.GLOWING_RING
        ),
        BackgroundOption.Shader(
            id = "shader_purple_gradient",
            name = "Purple Flow",
            previewColor = Color(0xFF9B59B6),
            shaderType = ShaderType.PURPLE_GRADIENT
        ),
        BackgroundOption.Shader(
            id = "shader_triangles",
            name = "Triangles",
            previewColor = Color(0xFF2C3E50),
            shaderType = ShaderType.MOVING_TRIANGLES
        )
    )

    fun getGradientOptions(): List<BackgroundOption> = listOf(
        BackgroundOption.Gradient(
            id = "gradient_sunset",
            name = "Sunset",
            previewColor = Color(0xFFFF6B6B),
            colors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE),
                Color(0xFFFF6B6B)
            )
        ),
        BackgroundOption.Gradient(
            id = "gradient_ocean",
            name = "Ocean",
            previewColor = Color(0xFF4A90E2),
            colors = listOf(
                Color(0xFF000428),
                Color(0xFF004e92)
            )
        ),
        BackgroundOption.Gradient(
            id = "gradient_forest",
            name = "Forest",
            previewColor = Color(0xFF134E5E),
            colors = listOf(
                Color(0xFF134E5E),
                Color(0xFF71B280)
            )
        ),
        BackgroundOption.Gradient(
            id = "gradient_purple",
            name = "Purple",
            previewColor = Color(0xFF667eea),
            colors = listOf(
                Color(0xFF667eea),
                Color(0xFF764ba2)
            )
        ),
        BackgroundOption.Gradient(
            id = "gradient_fire",
            name = "Fire",
            previewColor = Color(0xFFf12711),
            colors = listOf(
                Color(0xFFf12711),
                Color(0xFFf5af19)
            )
        ),
        BackgroundOption.Gradient(
            id = "gradient_mint",
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
            id = "solid_black",
            name = "Black",
            previewColor = Color.Black,
            color = Color.Black
        ),
        BackgroundOption.SolidColor(
            id = "solid_white",
            name = "White",
            previewColor = Color.White,
            color = Color.White
        ),
        BackgroundOption.SolidColor(
            id = "solid_gray",
            name = "Gray",
            previewColor = Color.Gray,
            color = Color.Gray
        ),
        BackgroundOption.SolidColor(
            id = "solid_dark_gray",
            name = "Dark Gray",
            previewColor = Color.DarkGray,
            color = Color.DarkGray
        ),
        BackgroundOption.SolidColor(
            id = "solid_blue",
            name = "Blue",
            previewColor = Color(0xFF2196F3),
            color = Color(0xFF2196F3)
        ),
        BackgroundOption.SolidColor(
            id = "solid_green",
            name = "Green",
            previewColor = Color(0xFF4CAF50),
            color = Color(0xFF4CAF50)
        ),
        BackgroundOption.SolidColor(
            id = "solid_purple",
            name = "Purple",
            previewColor = Color(0xFF9C27B0),
            color = Color(0xFF9C27B0)
        ),
        BackgroundOption.SolidColor(
            id = "solid_red",
            name = "Red",
            previewColor = Color(0xFFF44336),
            color = Color(0xFFF44336)
        )
    )
}