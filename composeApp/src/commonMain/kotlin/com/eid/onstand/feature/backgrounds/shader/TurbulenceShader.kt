package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object TurbulenceShader : Shader {
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
 * CC0: Turbulence experiment - converted to SkSL
 *
 * Original by @XorDev. This shader uses raymarching to create a 3D grid
 * that is distorted by layers of swirling turbulence.
 *
 * Twigl link: https://twigl.app?ol=true&ss=-OSU4MRk-98yz1dGL5Nh
 */

// These uniforms are expected to be provided by the host environment.
uniform float uTime;
uniform vec3 uResolution;

// --- Helper Functions ---

// Custom implementation of tanh for compatibility.
float Tanh(float x) {
    float ex = exp(x);
    float emx = exp(-x);
    return (ex - emx) / (ex + emx);
}

// Custom implementation of round for compatibility.
float Round(float x) {
    return floor(x + 0.5);
}

// --- MAIN FUNCTION (SkSL) ---
vec4 main(vec2 fragCoord) {
    // --- Initialization ---
    float t = 0.05 * uTime; // Time-based animation offset - SLOWED DOWN
    // Per-pixel "noise" to reduce banding artifacts
    float z = 0.1 * fract(dot(fragCoord, sin(fragCoord)));
    
    vec4 accumulated_color = vec4(0.0); // Accumulated color/lighting for this pixel

    // --- Raymarching Loop ---
    // March up to 77 steps along the ray.
    for (float i = 0.0; i < 77.0; i++) {
        // 1. Convert 2D screen coordinate to 3D ray direction.
        vec3 ray_dir = normalize(vec3(fragCoord - 0.5 * uResolution.xy, uResolution.y));
        vec4 p = vec4(z * ray_dir, 0.0);

        // 2. Offset the ray origin.
        p.xy += 6.0;
        p.z += t;

        // 3. Generate turbulence.
        float d = 4.0; // Initial frequency/scale for the turbulence
        vec4 P = p; // Save original position before turbulence
        
        // This loop adds chaotic, swirling distortion to the space.
        for (int j = 0; j < 5; j++) { // Using a fixed loop count is often more stable
            if (d >= 7.0) break;
            p += cos(p.zxyw * d + 0.6 * t) / d;
            d /= 0.8;
        }

        // 4. Calculate lighting/color based on turbulence.
        // The color is derived from how much the point moved.
        P = 1.2 + sin(vec4(0, 1, 2, 0) + 9.0 * length(P - p));

        // 5. Calculate the distance to the scene (the Signed Distance Function).
        // Wrap space into repeating unit cubes to create a grid.
        p.xyz -= vec3(Round(p.x), Round(p.y), Round(p.z));
        
        // Create a cross-shaped distance field.
        d = abs(min(length(p.yz), min(length(p.xy), length(p.xz))) - 0.1 * Tanh(z) + 2e-2);

        // 6. Accumulate lighting.
        // The lighting is brighter when closer to a surface.
        accumulated_color += P.w / max(d, 1e-3) * P;
        
        // 7. Advance the ray position.
        z += 0.2 * d + 1e-3;
    }

    // --- Tonemapping & Output ---
    // Use the custom Tanh function to compress bright values into a displayable range.
    vec4 final_color;
    final_color.r = Tanh(accumulated_color.r / 2e4);
    final_color.g = Tanh(accumulated_color.g / 2e4);
    final_color.b = Tanh(accumulated_color.b / 2e4);
    final_color.a = 1.0;

    return final_color;
}

    """
}