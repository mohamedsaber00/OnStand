package com.eid.onstand.core.data

import androidx.compose.ui.graphics.Color
import com.eid.onstand.core.models.*

class BackgroundRepository {

    fun getBackgroundTypes(): List<BackgroundType> = listOf(
        // Shader Backgrounds
        BackgroundType.Shader(
            id = "ether",
            name = "Ether",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.ETHER
        ),
        BackgroundType.Shader(
            id = "space",
            name = "Space",
            previewColor = Color(0xFF1A0033),
            shaderType = ShaderType.SPACE
        ),
        BackgroundType.Shader(
            id = "glowing_ring",
            name = "Glowing Ring",
            previewColor = Color(0xFF7B68EE),
            shaderType = ShaderType.GLOWING_RING
        ),
        BackgroundType.Shader(
            id = "purple_flow",
            name = "Purple Flow",
            previewColor = Color(0xFF9B59B6),
            shaderType = ShaderType.PURPLE_GRADIENT
        ),
        BackgroundType.Shader(
            id = "moving_triangles",
            name = "Triangles",
            previewColor = Color(0xFF2C3E50),
            shaderType = ShaderType.MOVING_TRIANGLES
        ),
        BackgroundType.Shader(
            id = "moving_waves",
            name = "Moving Waves",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.MOVING_WAVES
        ),
        BackgroundType.Shader(
            id = "turbulence",
            name = "Turbulence",
            previewColor = Color(0xFF8E44AD),
            shaderType = ShaderType.TURBULENCE
        ),

        // Live Animated Backgrounds
        BackgroundType.Live(
            id = "rotating_gradient",
            name = "Rotating",
            previewColor = Color(0xFF7B68EE),
            animationType = LiveAnimationType.ROTATING_GRADIENT
        ),
        BackgroundType.Live(
            id = "fog_effect",
            name = "Fog",
            previewColor = Color(0xFF2C3E40),
            animationType = LiveAnimationType.FOG_EFFECT
        ),
        BackgroundType.Live(
            id = "animated_particles",
            name = "Particles",
            previewColor = Color(0xFF4A90E2),
            animationType = LiveAnimationType.ANIMATED_PARTICLES
        )
    )

    fun getGradientTypes(): List<BackgroundType> = listOf(
        BackgroundType.Gradient(
            id = "sunset",
            name = "Sunset",
            previewColor = Color(0xFFFF6B6B),
            colors = listOf(
                Color(0xFF4A90E2),
                Color(0xFF7B68EE),
                Color(0xFFFF6B6B)
            ),
            direction = GradientDirection.DIAGONAL_UP
        ),
        BackgroundType.Gradient(
            id = "ocean",
            name = "Ocean",
            previewColor = Color(0xFF4A90E2),
            colors = listOf(
                Color(0xFF000428),
                Color(0xFF004e92)
            ),
            direction = GradientDirection.VERTICAL
        ),
        BackgroundType.Gradient(
            id = "forest",
            name = "Forest",
            previewColor = Color(0xFF134E5E),
            colors = listOf(
                Color(0xFF134E5E),
                Color(0xFF71B280)
            ),
            direction = GradientDirection.DIAGONAL_DOWN
        ),
        BackgroundType.Gradient(
            id = "purple",
            name = "Purple",
            previewColor = Color(0xFF667eea),
            colors = listOf(
                Color(0xFF667eea),
                Color(0xFF764ba2)
            ),
            direction = GradientDirection.VERTICAL
        ),
        BackgroundType.Gradient(
            id = "fire",
            name = "Fire",
            previewColor = Color(0xFFf12711),
            colors = listOf(
                Color(0xFFf12711),
                Color(0xFFf5af19)
            ),
            direction = GradientDirection.RADIAL
        ),
        BackgroundType.Gradient(
            id = "mint",
            name = "Mint",
            previewColor = Color(0xFF00b4db),
            colors = listOf(
                Color(0xFF00b4db),
                Color(0xFF0083b0)
            ),
            direction = GradientDirection.HORIZONTAL
        )
    )

    fun getSolidTypes(): List<BackgroundType> = listOf(
        BackgroundType.Solid(
            id = "black",
            name = "Black",
            previewColor = Color.Black,
            color = Color.Black
        ),
        BackgroundType.Solid(
            id = "white",
            name = "White",
            previewColor = Color.White,
            color = Color.White
        ),
        BackgroundType.Solid(
            id = "gray",
            name = "Gray",
            previewColor = Color.Gray,
            color = Color.Gray
        ),
        BackgroundType.Solid(
            id = "dark_gray",
            name = "Dark Gray",
            previewColor = Color.DarkGray,
            color = Color.DarkGray
        ),
        BackgroundType.Solid(
            id = "blue",
            name = "Blue",
            previewColor = Color(0xFF2196F3),
            color = Color(0xFF2196F3)
        ),
        BackgroundType.Solid(
            id = "green",
            name = "Green",
            previewColor = Color(0xFF4CAF50),
            color = Color(0xFF4CAF50)
        ),
        BackgroundType.Solid(
            id = "purple",
            name = "Purple",
            previewColor = Color(0xFF9C27B0),
            color = Color(0xFF9C27B0)
        ),
        BackgroundType.Solid(
            id = "red",
            name = "Red",
            previewColor = Color(0xFFF44336),
            color = Color(0xFFF44336)
        )
    )

    fun getPatternTypes(): List<BackgroundType> = listOf(
        BackgroundType.Pattern(
            id = "geometric_hex",
            name = "Hexagon",
            previewColor = Color(0xFF34495E),
            patternType = PatternType.GEOMETRIC,
            primaryColor = Color(0xFF34495E),
            secondaryColor = Color(0xFF2C3E50)
        ),
        BackgroundType.Pattern(
            id = "organic_circles",
            name = "Bubbles",
            previewColor = Color(0xFF3498DB),
            patternType = PatternType.ORGANIC,
            primaryColor = Color(0xFF3498DB),
            secondaryColor = Color(0xFF2980B9)
        ),
        BackgroundType.Pattern(
            id = "minimal_lines",
            name = "Lines",
            previewColor = Color(0xFF95A5A6),
            patternType = PatternType.MINIMAL,
            primaryColor = Color(0xFF95A5A6),
            secondaryColor = Color(0xFF7F8C8D)
        ),
        BackgroundType.Pattern(
            id = "artistic_swirl",
            name = "Swirl",
            previewColor = Color(0xFF9B59B6),
            patternType = PatternType.ARTISTIC,
            primaryColor = Color(0xFF9B59B6),
            secondaryColor = Color(0xFF8E44AD)
        )
    )

    // Legacy support methods - will be removed gradually
    fun getBackgroundOptions(): List<BackgroundOption> = listOf(
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
        ),
        BackgroundOption.Shader(
            id = "shader_moving_waves",
            name = "Moving Waves",
            previewColor = Color(0xFF4A90E2),
            shaderType = ShaderType.MOVING_WAVES
        ),
        BackgroundOption.Shader(
            id = "shader_turbulence",
            name = "Turbulence",
            previewColor = Color(0xFF8E44AD),
            shaderType = ShaderType.TURBULENCE
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