package com.eid.onstand.core.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.reflect.KClass

/**
 * Base class for all background effects in the application.
 * Uses class-based identification instead of string IDs for type safety.
 */
abstract class BackgroundEffect {
    abstract val displayName: String
    abstract val previewColor: Color
    abstract val category: BackgroundCategory
    
    @Composable
    abstract fun Render(modifier: Modifier = Modifier)
    
    /**
     * Unique identifier based on the class name.
     * This is type-safe and automatically derived.
     */
    val typeId: String get() = this::class.simpleName ?: "UnknownBackground"
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BackgroundEffect) return false
        return this::class == other::class
    }
    
    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

/**
 * Type-safe categories for background effects.
 */
enum class BackgroundCategory(val displayName: String) {
    SHADER("Shaders"),
    ANIMATED("Animated"),
    GRADIENT("Gradients"),
    SOLID_COLOR("Solid Colors"),
    PATTERN("Patterns")
}

/**
 * Registry for managing all available background effects.
 * Uses type-safe class-based registration.
 */
object BackgroundRegistry {
    internal val backgrounds = mutableMapOf<KClass<out BackgroundEffect>, BackgroundEffect>()
    
    fun register(background: BackgroundEffect) {
        backgrounds[background::class] = background
    }
    
    fun registerAll(backgrounds: List<BackgroundEffect>) {
        backgrounds.forEach { register(it) }
    }
    
    fun getAll(): List<BackgroundEffect> = backgrounds.values.toList()
    
    inline fun <reified T : BackgroundEffect> get(): T? {
        return getByClass(T::class) as? T
    }
    
    fun getByClass(clazz: KClass<out BackgroundEffect>): BackgroundEffect? {
        return backgrounds[clazz]
    }
    
    fun getByTypeId(typeId: String): BackgroundEffect? {
        return backgrounds.values.find { it.typeId == typeId }
    }
    
    fun clear() {
        backgrounds.clear()
    }
    
    fun getByCategory(): Map<BackgroundCategory, List<BackgroundEffect>> {
        return getAll().groupBy { it.category }
    }
    
    fun getCategory(category: BackgroundCategory): List<BackgroundEffect> {
        return getAll().filter { it.category == category }
    }
}

// Specific background effect types

/**
 * Shader-based background effects.
 */
abstract class ShaderBackgroundEffect : BackgroundEffect() {
    override val category = BackgroundCategory.SHADER
    abstract val shaderType: ShaderType
    open val speed: Float = 1.0f
}

/**
 * Animated background effects.
 */
abstract class AnimatedBackgroundEffect : BackgroundEffect() {
    override val category = BackgroundCategory.ANIMATED
    abstract val animationType: LiveAnimationType
    open val animationSpeed: Float = 1.0f
}

/**
 * Gradient background effects.
 */
abstract class GradientBackgroundEffect : BackgroundEffect() {
    override val category = BackgroundCategory.GRADIENT
    abstract val colors: List<Color>
    open val direction: GradientDirection = GradientDirection.VERTICAL
}

/**
 * Solid color background effects.
 */
abstract class SolidBackgroundEffect : BackgroundEffect() {
    override val category = BackgroundCategory.SOLID_COLOR
    abstract val color: Color
}