package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object PurpleGradientShader : Shader {
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
 * Mesh Gradient Variation 2 - Deeper Flow
 * SkSL conversion of a GLSL shader.
 *
 * This shader generates a complex, animated mesh gradient with
 * organic waves, rotation, and texture.
 */

// === USER CONTROLLABLE PARAMETERS ===

// Animation Speed Control
const float ANIMATION_SPEED = 0.2; // Multiplier for all time-based animations (0.1 = very slow, 2.0 = fast)

// Wave Distortion Controls
const float WAVE_INTENSITY = 1.0;   // Overall wave strength (0.0 = no waves, 2.0 = intense)
const float WAVE_COMPLEXITY = 1.0;  // Number of wave layers (0.5 = simple, 2.0 = complex)

// Rotation Controls
const float ROTATION_SPEED = 1.0;   // Speed of rotation animation (0.0 = no rotation, 2.0 = fast spin)
const float ROTATION_AMOUNT = 1.0;  // Intensity of rotation effect (0.0 = no rotation, 2.0 = strong)

// Color Palette Parameters (Inigo Quilez algorithm)
const int NUM_STOPS = 6;            // Number of color stops to generate (2-8 recommended)
const vec4 PALETTE_A = vec4(0.9, 0.5, 0.6, 1.0);    // Offset (base color)
const vec4 PALETTE_B = vec4(0.3, 0.4, 0.5, 1.0);    // Amplitude (contrast)
const vec4 PALETTE_C = vec4(1.5, 0.8, 1.2, 1.0);    // Frequency
const vec4 PALETTE_D = vec4(0.2, 0.5, 0.8, 1.0);    // Phase shift

// Gradient Zone Controls
const float BLEND_SOFTNESS = 1.0;   // How soft the color transitions are (0.1 = sharp, 2.0 = very soft)

// Texture Controls
const float TEXTURE_INTENSITY = 1.0; // Strength of texture overlay (0.0 = smooth, 2.0 = textured)
const float TEXTURE_SCALE = 1.0;     // Size of texture details (0.5 = large, 2.0 = fine)

// === UNIFORMS ===
uniform float uTime;
uniform vec3 uResolution;

// === SHADER CODE ===

// Simple 2D rotation matrix
mat2 rotate(float angle) {
    float c = cos(angle);
    float s = sin(angle);
    return mat2(c, -s, s, c);
}

// Original hash function for noise generation
vec2 hash22(vec2 p) {
    p = vec2(dot(p, vec2(157.13, 113.47)), dot(p, vec2(271.19, 419.23)));
    return fract(sin(p) * 19371.5813);
}

// Custom noise implementation
float customNoise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    
    // Cubic interpolation
    vec2 u = f * f * (3.0 - 2.0 * f);
    
    float a = dot(hash22(i) - 0.5, f);
    float b = dot(hash22(i + vec2(1.0, 0.0)) - 0.5, f - vec2(1.0, 0.0));
    float c = dot(hash22(i + vec2(0.0, 1.0)) - 0.5, f - vec2(0.0, 1.0));
    float d = dot(hash22(i + vec2(1.0, 1.0)) - 0.5, f - vec2(1.0, 1.0));
    
    return 0.5 + mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

// Smooth blending function (smoothstep)
float blend(float edge0, float edge1, float x) {
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return t * t * (3.0 - 2.0 * t);
}

// Inigo Quilez's palette function for user-controllable colors
vec3 palette(float t, vec3 a, vec3 b, vec3 c, vec3 d) {
    return a + b * cos(6.28318 * (c * t + d));
}

// Main entry point for the shader
vec4 main(vec2 fragCoord) {
    vec2 uv = fragCoord / uResolution.xy;
    vec2 coord = uv - 0.5;
    
    // Aspect ratio correction
    coord.x *= uResolution.x / uResolution.y;
    
    // Apply organic rotation with user-controlled speed and intensity
    float rotationNoise = customNoise(vec2(uTime * 0.05 * ANIMATION_SPEED * ROTATION_SPEED, coord.x * coord.y * 1.5));
    coord *= rotate((rotationNoise - 0.5) * 4.0 * ROTATION_AMOUNT + uTime * 0.06 * ANIMATION_SPEED * ROTATION_SPEED);
    
    // Wave distortion with user-controlled intensity and complexity
    float waveTime = uTime * 0.8 * ANIMATION_SPEED;
    float freq1 = 2.5 * WAVE_COMPLEXITY;
    float freq2 = 3.2 * WAVE_COMPLEXITY;
    
    coord.x += sin(coord.y * freq1 + waveTime) * 0.15 * WAVE_INTENSITY;
    coord.y += cos(coord.x * freq2 + waveTime * 1.3) * 0.12 * WAVE_INTENSITY;
    coord.x += sin(coord.y * freq2 * 0.6 + waveTime * 0.7) * 0.08 * WAVE_INTENSITY;
    coord.y += cos(coord.x * freq1 * 1.8 + waveTime * 1.1) * 0.06 * WAVE_INTENSITY;
    
    // Use user-defined palette parameters
    vec3 a = PALETTE_A.rgb;
    vec3 b = PALETTE_B.rgb;
    vec3 c = PALETTE_C.rgb;
    vec3 d = PALETTE_D.rgb;
    
    // Generate colors based on NUM_STOPS
    vec3 colors[8]; // Maximum 8 colors
    for(int i = 0; i < 8; i++) {
        if(i < NUM_STOPS) {
            float t = (NUM_STOPS > 1) ? float(i) / float(NUM_STOPS - 1) : 0.0;
            colors[i] = palette(t, a, b, c, d);
        }
    }
    
    // Create gradient zones with user-controlled softness
    float blendRange = 0.7 * BLEND_SOFTNESS;
    float zone1 = blend(-blendRange, blendRange, (coord * rotate(0.3)).x);
    vec3 horizontalBlend = mix(colors[0], colors[1], zone1);
    
    float zone2 = blend(-blendRange, blendRange, (coord * rotate(-0.2)).x);
    vec3 horizontalBlend2;
    if(NUM_STOPS >= 4) {
        horizontalBlend2 = mix(colors[2], colors[3], zone2);
    } else if(NUM_STOPS >= 3) {
        horizontalBlend2 = mix(colors[1], colors[2], zone2);
    } else {
        horizontalBlend2 = mix(colors[0], colors[1], zone2);
    }
    
    // Vertical blending with user-controlled softness
    float verticalZone = blend(0.3 * BLEND_SOFTNESS, -0.5 * BLEND_SOFTNESS, coord.y + sin(coord.x * 2.0) * 0.2);
    vec3 finalColor = mix(horizontalBlend, horizontalBlend2, verticalZone);
    
    // Add user-controlled texture variation
    float texture1 = customNoise(coord * 6.0 * TEXTURE_SCALE + uTime * 0.03 * ANIMATION_SPEED) * 0.04 * TEXTURE_INTENSITY;
    float texture2 = customNoise(coord * 12.0 * TEXTURE_SCALE - uTime * 0.02 * ANIMATION_SPEED) * 0.02 * TEXTURE_INTENSITY;
    finalColor += texture1 + texture2;
    
    // Color processing
    finalColor = pow(finalColor, vec3(0.85));
    finalColor = clamp(finalColor, 0.0, 1.0);
    
    return vec4(finalColor, 1.0);
}

    """
}