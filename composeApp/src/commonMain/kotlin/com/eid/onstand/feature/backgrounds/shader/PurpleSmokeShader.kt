package com.eid.onstand.feature.backgrounds.shader

import com.eid.onstand.core.ui.shaders.Shader


object PurpleSmokeShader : Shader {

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
uniform float filmGrainIntensity;
uniform float uTime;
uniform vec3  uResolution;

// --------------------------------------------------
// Utility 
// --------------------------------------------------
mat2 Rot(float a) {
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);
}

vec2 hash(vec2 p) {
    p = vec2(dot(p, vec2(2127.1, 81.17)),
             dot(p, vec2(1269.5, 283.37)));
    return fract(sin(p) * 43758.5453);
}

float noise(in vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f*f*(3.0 - 2.0*f);

    float n = mix(
        mix(dot(-1.0 + 2.0 * hash(i + vec2(0.0, 0.0)), f - vec2(0.0, 0.0)),
            dot(-1.0 + 2.0 * hash(i + vec2(1.0, 0.0)), f - vec2(1.0, 0.0)), u.x),
        mix(dot(-1.0 + 2.0 * hash(i + vec2(0.0, 1.0)), f - vec2(0.0, 1.0)),
            dot(-1.0 + 2.0 * hash(i + vec2(1.0, 1.0)), f - vec2(1.0, 1.0)), u.x), u.y);
    return 0.5 + 0.5 * n;
}

float filmGrainNoise(in vec2 uv) {
    return length(hash(vec2(uv.x, uv.y)));
}

// --------------------------------------------------
// Main
// --------------------------------------------------
vec4 main(vec2 fragCoord) {
    vec2 uv = fragCoord / uResolution.xy;
    float aspectRatio = uResolution.x / uResolution.y;

    vec2 tuv = uv - 0.5;

    // Slightly slower rotation (time factor from 0.015 -> 0.010)
    float degree = noise(vec2(uTime * 0.010, tuv.x * tuv.y));
    tuv.y *= 1.0 / aspectRatio;
    tuv *= Rot(radians((degree - 0.5) * 720.0 + 180.0));
    tuv.y *= aspectRatio;

    // Warp (speed from 0.5 -> 0.38)
    float frequency = 5.0;
    float amplitude = 30.0;
    float speed     = uTime * 0.38;
    tuv.x += sin(tuv.y * frequency + speed) / amplitude;
    tuv.y += sin(tuv.x * frequency * 1.5 + speed) / (amplitude * 0.5);

    vec3 licorice  = vec3(0.0627, 0.0118, 0.0667); // #100311
    vec3 indigo    = vec3(0.3451, 0.0000, 0.5725); // #580092
    vec3 wisteria  = vec3(0.8039, 0.6118, 0.9255); // #cd9cec

    vec3 indigoLift   = mix(indigo, wisteria, 0.30);
    vec3 deepBlend    = mix(licorice, indigo, 0.55);
    vec3 wisteriaSoft = mix(wisteria, indigo, 0.20);

    float cycle = sin(uTime * 0.13);
    float t = (sign(cycle) * pow(abs(cycle), 0.6) + 1.0) * 0.5;

    vec3 color1 = mix(indigoLift, wisteria,    t);
    vec3 color2 = mix(deepBlend,  indigo,      t);
    vec3 color3 = mix(licorice,   indigoLift,  t*0.85);
    vec3 color4 = mix(indigo,     wisteriaSoft,t);

    vec2 ruv = Rot(radians(-5.0)) * tuv;
    vec3 layer1 = mix(color3, color2, smoothstep(-0.03, 0.20, ruv.x));
    vec3 layer2 = mix(color4, color1, smoothstep(-0.02, 0.20, ruv.x));
    vec3 color  = mix(layer1, layer2, smoothstep(0.05, -0.30, tuv.y));

    color *= 1.2;
    color = pow(color, vec3(1.1));

    color -= filmGrainNoise(uv) * filmGrainIntensity;

    return vec4(clamp(color, 0.0, 1.0), 1.0);
}
    """
}