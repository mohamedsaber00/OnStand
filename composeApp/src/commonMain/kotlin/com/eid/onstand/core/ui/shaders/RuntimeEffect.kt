package com.eid.onstand.core.ui.shaders

import androidx.compose.ui.graphics.Brush

interface RuntimeEffect {
    val supported: Boolean
    val ready: Boolean

    fun setFloatUniform(name: String, value1: Float) {}
    fun setFloatUniform(name: String, value1: Float, value2: Float) {}
    fun setFloatUniform(name: String, value1: Float, value2: Float, value3: Float) {}
    fun setFloatUniform(name: String, values: FloatArray) {}

    fun update(shader: Shader, time: Float, width: Float, height: Float) {}
    fun build(): Brush
}

internal expect fun buildEffect(shader: Shader): RuntimeEffect