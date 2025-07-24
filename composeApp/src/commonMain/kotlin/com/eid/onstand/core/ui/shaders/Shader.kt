package com.eid.onstand.core.ui.shaders

interface Shader {
    val speedModifier: Float get() = 0.5f
    val sksl: String

    fun applyUniforms(runtimeEffect: RuntimeEffect, time: Float, width: Float, height: Float) {
        runtimeEffect.setFloatUniform("uResolution", width, height, width / height)
        runtimeEffect.setFloatUniform("uTime", time)
    }
}