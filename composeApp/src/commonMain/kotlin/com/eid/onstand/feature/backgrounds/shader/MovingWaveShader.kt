package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object MovingWaveShader : Shader {
    override val name: String
        get() = "Hell"

    override val authorName: String
        get() = "Inigo Quilez (ported by you)"

    override val authorUrl: String
        get() = "https://iquilezles.org/"

    override val credit: String
        get() = "https://www.shadertoy.com/view/XdfGzH"

    override val license: String
        get() = "Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License"

    override val licenseUrl: String
        get() = "https://www.shadertoy.com/terms"

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/**
 * Realistic Moving Colored Waves (Layered Hills)
 *
 * Features:
 * ✅ Multiple layered wavy hills (purple → pink → orange).
 * ✅ Smooth, natural motion.
 * ✅ Clear separation from the sky.
 */

uniform float uTime;
uniform vec3 uResolution;

// Color gradient for waves (t goes from 0 bottom to 1 top)
vec3 waveColor(float t) {
    return mix(
        vec3(1.0, 0.7, 0.4),                      // orange bottom
        mix(vec3(1.0, 0.4, 0.7), vec3(0.5, 0.3, 1.0), t), // pink → purple
        t
    );
}

vec4 main(vec2 fragCoord) {
    // Normalize 0..1
    vec2 uv = fragCoord / uResolution.xy;
    uv.y = 1.0 - uv.y; // flip for correct orientation

    vec3 color = vec3(0.7, 0.6, 1.0); // default sky (soft light purple)

    float time = uTime * 0.3;

    // Draw 3 layers of hills (from back to front)
    for (int i = 0; i < 3; i++) {
        float layer = float(i);

        // Wave height and speed per layer
        float height = 0.25 + layer * 0.1;
        float speed = 0.2 + layer * 0.15;
        float offset = layer * 0.2;

        // Wavy hill shape
        float waveY = sin(uv.x * (2.0 + layer) + time * speed + offset) * 0.05
                    + 0.3 + layer * 0.2;

        // Fill the hill if below the wave line
        if (uv.y < waveY) {
            float t = clamp((waveY - uv.y) * 2.0, 0.0, 1.0);
            color = mix(color, waveColor(t), 0.8);
        }
    }

    return vec4(color, 1.0);
}
    """
}