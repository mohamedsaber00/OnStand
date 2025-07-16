package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object SpaceShader : Shader {
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
 * Peaceful 2D Starfield & Cinematic Meteor Shader (Final Stable Version)
 *
 * Features:
 * ✅ Stars twinkle with subtle brightness pulses.
 * ✅ Meteors have random colors based on palettes.
 * ✅ Meteor trails linger and fade out after passing.
 * ✅ Only ONE meteor at a time, every ~25 seconds.
 * ✅ Infinite cycles (no stopping after many meteors).
 */

uniform float uTime;
uniform vec3 uResolution;

// --- Configurable Options ---
const float SPEED = 0.1;
const float STAR_DENSITY = 0.8;
const float STAR_BRIGHTNESS = 8.0;
const int PALETTE = -1; // -1 = mixed stars, 0–4 = single palette

// --- Constants ---
const float PI = 3.1415926535;
const float TAU = 6.283185307;

// --- Helper Functions ---
float hash21(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

vec2 hash22(vec2 p) {
    float n = sin(dot(p, vec2(41.0, 289.0)));
    return fract(vec2(262144.0, 32768.0) * n);
}

float distToSegment(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a, ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    return length(pa - ba * h);
}

vec3 cosPalette(float t, int paletteId) {
    if (paletteId == 0) return vec3(1.0);
    vec3 a, b, c, d;
    if (paletteId == 1){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0); d=vec3(0.00,0.10,0.20);}
    else if (paletteId == 2){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0,0.7,0.4); d=vec3(0.00,0.15,0.20);}
    else if (paletteId == 3){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0); d=vec3(0.30,0.40,0.50);}
    else if (paletteId == 4){ a=vec3(0.8,0.5,0.4); b=vec3(0.2,0.4,0.2); c=vec3(2.0,1.0,1.0); d=vec3(0.00,0.25,0.25);}
    else { return vec3(1.0);}
    return a + b * cos(TAU * (c * t + d));
}

// --- Main Render Function ---
vec4 main(vec2 fragCoord) {
    // 1. Normalize & drifting starfield UV
    vec2 uv = (fragCoord.xy - uResolution.xy * 0.5) / uResolution.y * 10.0;
    uv += vec2(uTime * SPEED, uTime * SPEED * 0.5);

    vec3 finalColor = vec3(0.0);

    // 2. Starfield with Twinkling
    vec2 grid_id = floor(uv);
    vec2 frag_id = fract(uv);

    for (float y = -1.0; y <= 1.0; y++) {
        for (float x = -1.0; x <= 1.0; x++) {
            vec2 cell = grid_id + vec2(x, y);

            float cell_hash = hash21(cell);
            if (cell_hash > STAR_DENSITY) continue;

            vec2 star_pos = hash22(cell);
            float star_brightness = hash21(cell + 1.0) * STAR_BRIGHTNESS;
            float dist = length(frag_id - star_pos + vec2(x, y));

            float star_glow = smoothstep(0.05, 0.0, dist);
            float star_core = smoothstep(0.01, 0.0, dist);

            float twinkle = 0.75 + 0.25 * sin(uTime * 2.0 + cell_hash * 10.0);

            int paletteToUse = (PALETTE < 0) ? int(floor(hash21(cell) * 5.0)) : PALETTE;
            vec3 star_color = cosPalette(cell_hash, paletteToUse);

            finalColor += (star_glow * 0.5 + star_core) * star_brightness * star_color * twinkle;
        }
    }

    // 3. Cinematic Meteor (One at a time, infinite)
    float epoch_duration = 25.0;      // one meteor every ~25 seconds
    float epoch = mod(floor(uTime / epoch_duration), 10000.0); // ✅ keeps seed fresh forever
    float time_in_epoch = mod(uTime, epoch_duration);
    float meteor_duration = 8.0;

    if (time_in_epoch < meteor_duration) {
        float start_angle = hash21(vec2(epoch, 10.0)) * TAU;
        float end_angle = start_angle + PI + (hash21(vec2(epoch, 99.0)) - 0.5);
        float radius = 7.0;

        vec2 start_pos = vec2(cos(start_angle), sin(start_angle)) * radius;
        vec2 end_pos   = vec2(cos(end_angle), sin(end_angle)) * radius;

        vec2 path = end_pos - start_pos;
        float life = time_in_epoch / meteor_duration;

        vec2 trail_end = start_pos + path * life;
        vec2 trail_start = trail_end - normalize(path) * 2.0;

        float dist = distToSegment(uv, trail_start, trail_end);
        float meteor_glow = smoothstep(0.3, 0.0, dist);
        float meteor_core = smoothstep(0.03, 0.0, dist);

        float fade = smoothstep(0.0, 1.0, sin(life * PI));
        float t = clamp(dot(uv - trail_start, normalize(path)) /
                        length(trail_end - trail_start), 0.0, 1.0);
        float trail_fade = smoothstep(0.0, 1.0, t);

        int meteorPalette = int(floor(hash21(vec2(epoch, 55.0)) * 5.0));
        vec3 meteorColor = cosPalette(life, meteorPalette);

        float linger = smoothstep(0.0, 1.0, (1.0 - life));

        finalColor += (meteor_glow * 0.2 + meteor_core) *
                      meteorColor * fade * trail_fade * 3.0;
        finalColor += meteor_glow * 0.1 * meteorColor * linger;
    }

    // 4. Tonemapping & Output
    finalColor = 1.0 - exp(-finalColor);
    finalColor = pow(finalColor, vec3(0.8));

    return vec4(finalColor, 1.0);
}
    """
}