package com.eid.onstand.feature.backgrounds.shader

import com.eid.onstand.core.shaders.Shader


object GlowingRing : Shader {

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/**
 * Glowing Ring Shader
 *
 * This SkSL shader generates a dynamic, glowing ring with a soft,
 * noisy texture and a moving highlight.
 *
 * This is a conversion of a GLSL shader.
 * Original author unknown, noise from https://www.shadertoy.com/view/4sc3z2
 */

uniform float uTime;
uniform vec3 uResolution;

// --- Constants ---
const float innerRadius = 0.6;
const float noiseScale = 0.65;

// --- Static Colors ---
const vec3 color1 = vec3(0.611765, 0.262745, 0.996078);
const vec3 color2 = vec3(0.298039, 0.760784, 0.913725);
const vec3 color3 = vec3(0.062745, 0.078431, 0.600000);

// --- Noise Functions ---
vec3 hash33(vec3 p3) {
    p3 = fract(p3 * vec3(0.1031, 0.11369, 0.13787));
    p3 += dot(p3, p3.yxz + 19.19);
    return -1.0 + 2.0 * fract(vec3(p3.x + p3.y, p3.x + p3.z, p3.y + p3.z) * p3.zyx);
}

float snoise3(vec3 p) {
    const float K1 = 0.333333333;
    const float K2 = 0.166666667;

    vec3 i = floor(p + (p.x + p.y + p.z) * K1);
    vec3 d0 = p - (i - (i.x + i.y + i.z) * K2);

    vec3 e = step(vec3(0.0), d0 - d0.yzx);
    vec3 i1 = e * (1.0 - e.zxy);
    vec3 i2 = 1.0 - e.zxy * (1.0 - e);

    vec3 d1 = d0 - (i1 - K2);
    vec3 d2 = d0 - (i2 - K1);
    vec3 d3 = d0 - 0.5;

    vec4 h = max(0.6 - vec4(dot(d0, d0), dot(d1, d1), dot(d2, d2), dot(d3, d3)), 0.0);
    vec4 n = h * h * h * h * vec4(dot(d0, hash33(i)), dot(d1, hash33(i + i1)), dot(d2, hash33(i + i2)), dot(d3, hash33(i + 1.0)));

    return dot(vec4(31.316), n);
}

// --- Helper Functions ---
vec4 extractAlpha(vec3 colorIn) {
    vec4 colorOut;
    float maxValue = min(max(max(colorIn.r, colorIn.g), colorIn.b), 1.0);
    if (maxValue > 1e-5) {
        colorOut.rgb = colorIn.rgb * (1.0 / maxValue);
        colorOut.a = maxValue;
    } else {
        colorOut = vec4(0.0);
    }
    return colorOut;
}

float light1(float intensity, float attenuation, float dist) {
    return intensity / (1.0 + dist * attenuation);
}

float light2(float intensity, float attenuation, float dist) {
    return intensity / (1.0 + dist * dist * attenuation);
}

// --- Main Entry Point ---
vec4 main(vec2 fragCoord) {
    vec2 uv = (fragCoord * 2.0 - uResolution.xy) / uResolution.y;
    
    // --- Drawing Logic ---
    float ang = atan(uv.y, uv.x);
    float len = length(uv);
    float v0, v1, v2, v3, cl;
    float r0, d0, n0;
    float r, d;

    // ring
    n0 = snoise3(vec3(uv * noiseScale, uTime * 0.1)) * 0.5 + 0.5; // Slower noise
    r0 = mix(mix(innerRadius, 1.0, 0.4), mix(innerRadius, 1.0, 0.6), n0);
    d0 = distance(uv, r0 / len * uv);
    v0 = light1(1.0, 10.0, d0);
    v0 *= smoothstep(r0 * 1.05, r0, len);
    cl = cos(ang + uTime * 0.4) * 0.5 + 0.5; // Slower color rotation

    // high light
    float a = uTime * -0.2; // Slower highlight
    vec2 pos = vec2(cos(a), sin(a)) * r0;
    d = distance(uv, pos);
    v1 = light2(1.5, 5.0, d);
    v1 *= light1(1.0, 50.0, d0);

    // back decay
    v2 = smoothstep(1.0, mix(innerRadius, 1.0, n0 * 0.5), len);

    // hole
    v3 = smoothstep(innerRadius, mix(innerRadius, 1.0, 0.5), len);

    // color
    vec3 c = mix(color1, color2, cl);
    vec3 col = mix(color1, color2, cl);
    col = mix(color3, col, v0);
    col = (col + v1) * v2 * v3;
    col.rgb = clamp(col.rgb, 0.0, 1.0);
    
    vec4 extractedColor = extractAlpha(col);
    
    // --- Background and Blending ---
    vec3 bg = vec3(0.0); // Static black background
    vec3 finalColor = mix(bg, extractedColor.rgb, extractedColor.a); // Normal blend

    return vec4(finalColor, 1.0);
}

    """
}