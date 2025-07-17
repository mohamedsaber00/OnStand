package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object EtherShader : Shader {
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
 * Ether Shader (Centered & Slowed Down)
 *
 * ✅ Centered properly on any screen resolution.
 * ✅ Slower, smoother evolution with adjustable timeSpeed.
 */

uniform float uTime;
uniform vec3 uResolution;

// Animation speed (smaller = slower)
const float timeSpeed = 0.2;

// Simple 2D rotation matrix
mat2 rotate(float angle) {
    float c = cos(angle), s = sin(angle);
    return mat2(c, -s, s, c);
}

// Distance field for volumetric clouds
float map(vec3 p) {
    float t = uTime * timeSpeed;
    p.xz *= rotate(t * 0.4);
    p.xy *= rotate(t * 0.3);

    vec3 q = p * 2.0 + t;
    return length(p + vec3(sin(t * 0.7), 0.0, 0.0)) * log(length(p) + 1.0)
         + sin(q.x + sin(q.z + sin(q.y))) * 0.5 - 1.0;
}

// Main function
vec4 main(vec2 fragCoord) {
    // ✅ Centered UV: (-1 to 1, centered)
    vec2 uv = (fragCoord - 0.5 * uResolution.xy) / uResolution.y;

    vec3 finalColor = vec3(0.0);
    float dist = 2.5;

    for (int i = 0; i <= 5; i++) {
        vec3 p = vec3(0.0, 0.0, 5.0) + normalize(vec3(uv, -1.0)) * dist;

        float rz = map(p);
        float lighting = clamp((rz - map(p + 0.1)) * 0.5, -0.1, 1.0);
        vec3 lightColor = vec3(0.1, 0.3, 0.4) + vec3(5.0, 2.5, 3.0) * lighting;

        finalColor = finalColor * lightColor + smoothstep(2.5, 0.0, rz) * 0.7 * lightColor;
        dist += min(rz, 1.0);
    }

    return vec4(finalColor, 1.0);
}
    """
}