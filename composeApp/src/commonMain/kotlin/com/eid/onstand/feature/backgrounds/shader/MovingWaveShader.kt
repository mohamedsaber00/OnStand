package com.eid.onstand.feature.backgrounds.shader

import com.eid.onstand.core.ui.shaders.Shader

object MovingWaveShader : Shader {


    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/**
 * Layered Waves Shader (SkSL)
 *
 * This shader creates multiple layers of sine waves to simulate a
 * stylized, glowing ocean effect.
 */

// These uniforms are expected to be provided by the host environment.
uniform float uTime;
uniform vec3 uResolution;

const float PI = 3.1415926535;


float wave(vec2 uv, vec4 amps, vec4 freqs, vec4 offset) {
    // Slow down the animation for a more peaceful feel.
    float time = uTime * 0.2;

    float x = uv.x;
    float y = 0.0;
    y += amps.x * sin(freqs.x * x + time + offset.x);
    y += amps.y * sin(freqs.y * x + time + offset.y);
    y += amps.z * sin(freqs.z * x + time + offset.z);
    y += amps.w * sin(freqs.w * x + time + offset.w);

    float blur = 0.025;
    
    float top_wave = smoothstep(y + blur, y, uv.y);
    float bottom_wave = smoothstep(y - 1.0, y, uv.y) * 0.4;

    return top_wave * bottom_wave;
}

// --- MAIN FUNCTION (SkSL) ---
vec4 main(vec2 fragCoord) {
    vec2 uv = 2.0 * (2.0 * fragCoord.xy - uResolution.xy) / uResolution.y;
    

    vec3 background_color = mix(vec3(0.05, 0.0, 0.15), vec3(0.2, 0.3, 0.6), (fragCoord.y / uResolution.y));
    vec4 final_color = vec4(background_color, 1.0);


    float f = 0.0;
    // Each call to wave() adds a new, distinct layer of waves.
    // The parameters have been adjusted for a calmer motion.
    f += wave(uv, vec4(0.1, 0.2, 0.1, 0.05), vec4(0.1, 0.4, 0.8, 0.3), vec4(1.0, 1.5, 2.0, 2.5) * PI);
    f += wave(uv, vec4(0.1, 0.15, 0.2, 0.1), vec4(0.8, 0.5, 0.4, 0.3), vec4(5.0, 2.0, 1.0, 3.0));
    f += wave(uv, vec4(0.2, 0.1, 0.05, 0.1), vec4(0.9, 0.5, 0.1, 0.1), vec4(1.0, 2.0, 2.0, 3.0));

    // Use a soft, glowing white/light blue for a more ethereal feel.
    vec3 wave_color = vec3(0.8, 0.9, 1.0);
    
    final_color += vec4(f * wave_color, 1.0);
    
    return final_color;
}


    """
}