package com.eid.onstand.core.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import kotlinx.datetime.LocalDateTime
import kotlin.reflect.KClass

/**
 * Interface for all clock widgets in the application.
 * Uses class-based identification instead of string IDs for type safety.
 */
abstract class ClockWidget {
    abstract val displayName: String
    abstract val supportsSeconds: Boolean
    abstract val isDigital: Boolean
    
    /**
     * Unique identifier based on the class name.
     * This is type-safe and automatically derived.
     */
    val typeId: String get() = this::class.simpleName ?: "UnknownClock"
    
    @Composable
    abstract fun Render(
        currentTime: LocalDateTime,
        showSeconds: Boolean = true,
        fontFamily: FontFamily = FontFamily.ROBOTO,
        textColor: Color = Color.White,
        isPreview: Boolean = false,
        hazeState: HazeState? = null,
        modifier: Modifier = Modifier
    )
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClockWidget) return false
        return this::class == other::class
    }
    
    override fun hashCode(): Int {
        return this::class.hashCode()
    }
}

/**
 * Registry for managing all available clock widgets.
 * Uses type-safe class-based registration.
 */
object ClockRegistry {
    internal val clocks = mutableMapOf<KClass<out ClockWidget>, ClockWidget>()
    
    fun register(clock: ClockWidget) {
        clocks[clock::class] = clock
    }
    
    fun registerAll(clocks: List<ClockWidget>) {
        clocks.forEach { register(it) }
    }
    
    fun getAll(): List<ClockWidget> = clocks.values.toList()
    
    inline fun <reified T : ClockWidget> get(): T? {
        return getByClass(T::class) as? T
    }
    
    fun getByClass(clazz: KClass<out ClockWidget>): ClockWidget? {
        return clocks[clazz]
    }
    
    fun getByTypeId(typeId: String): ClockWidget? {
        return clocks.values.find { it.typeId == typeId }
    }
    
    fun getDigitalClocks(): List<ClockWidget> = clocks.values.filter { it.isDigital }
    
    fun getAnalogClocks(): List<ClockWidget> = clocks.values.filter { !it.isDigital }
    
    fun clear() {
        clocks.clear()
    }
}

// Base clock widget types

/**
 * Base class for digital clock widgets.
 */
abstract class DigitalClockWidget : ClockWidget() {
    override val isDigital = true
    override val supportsSeconds = true
}

/**
 * Base class for analog clock widgets.
 */
abstract class AnalogClockWidget : ClockWidget() {
    override val isDigital = false
    override val supportsSeconds = false
}