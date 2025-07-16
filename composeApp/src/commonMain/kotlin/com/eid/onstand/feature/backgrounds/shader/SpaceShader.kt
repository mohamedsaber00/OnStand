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
 * Peaceful 2D Starfield & Meteor Shader
 *
 * This SkSL shader generates a calm, animated 2D starfield with softly
 * glowing stars and a periodic meteor streak. This version uses a robust
 * 2D procedural method to ensure broad compatibility, avoiding complex
 * 3D raymarching.
 *
 * Features:
 * - A slow-drifting, infinite 2D starfield.
 * - Softly glowing stars with varied brightness.
 * - A gentle, recurring meteor with a light trail.
 * - Multiple color palettes for a customizable mood.
 */

uniform float uTime;
uniform vec3 uResolution;

// --- Configurable Options ---
const float SPEED = 0.05;       // Controls the drifting speed of the starfield.
const float STAR_DENSITY = 0.6; // Value from 0.0 to 1.0. Higher means more stars.
const float STAR_BRIGHTNESS = 2.5;
const int PALETTE = 3;          // 0:White, 1:Vaporwave, 2:Fire, 3:Ice, 4:Nebula

// --- Constants ---
const float PI = 3.1415926535;
const float TAU = 2.0 * PI;

// --- Helper Functions ---

// Generates a pseudo-random float from a 2D vector.
float hash21(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

// Generates a pseudo-random 2D vector from a 2D vector.
vec2 hash22(vec2 p) {
	float n = sin(dot(p, vec2(41, 289)));
	return fract(vec2(262144, 32768) * n);
}

// Distance to a line segment function.
float distToSegment(vec2 p, vec2 a, vec2 b) {
    vec2 pa = p - a, ba = b - a;
    float h = clamp(dot(pa, ba) / dot(ba, ba), 0.0, 1.0);
    return length(pa - ba * h);
}

// Color palette function.
vec3 cosPalette(float t, int paletteId) {
    if (paletteId == 0) return vec3(1.0);
    vec3 a, b, c, d;
    if (paletteId == 1) { a = vec3(0.5, 0.5, 0.5); b = vec3(0.5, 0.5, 0.5); c = vec3(1.0, 1.0, 1.0); d = vec3(0.00, 0.10, 0.20); }
    else if (paletteId == 2) { a = vec3(0.5, 0.5, 0.5); b = vec3(0.5, 0.5, 0.5); c = vec3(1.0, 0.7, 0.4); d = vec3(0.00, 0.15, 0.20); }
    else if (paletteId == 3) { a = vec3(0.5, 0.5, 0.5); b = vec3(0.5, 0.5, 0.5); c = vec3(1.0, 1.0, 1.0); d = vec3(0.30, 0.40, 0.50); }
    else if (paletteId == 4) { a = vec3(0.8, 0.5, 0.4); b = vec3(0.2, 0.4, 0.2); c = vec3(2.0, 1.0, 1.0); d = vec3(0.00, 0.25, 0.25); }
    else { return vec3(1.0); }
    return a + b * cos(TAU * (c * t + d));
}

// --- Main Render Function ---
vec4 main(vec2 fragCoord) {
    // 1. Normalize and set up coordinates.
    // We scale by 10 to have more grid cells for stars.
    vec2 uv = (fragCoord.xy - uResolution.xy * 0.5) / uResolution.y * 10.0;
    
    // Add camera motion for a drifting effect.
    uv += vec2(uTime * SPEED, uTime * SPEED * 0.5);

    // 2. Starfield Generation.
    vec3 finalColor = vec3(0.0);
    
    // Get the integer and fractional parts of the coordinates.
    vec2 grid_id = floor(uv);
    vec2 frag_id = fract(uv);

    // Iterate over a 3x3 grid around the current fragment.
    // This ensures stars don't pop in/out at cell edges.
    for (float y = -1.0; y <= 1.0; y++) {
        for (float x = -1.0; x <= 1.0; x++) {
            vec2 cell = grid_id + vec2(x, y);
            
            // Use a hash to decide if a star exists in this cell.
            float cell_hash = hash21(cell);
            if (cell_hash > STAR_DENSITY) continue;
            
            // Get a random position and brightness for the star.
            vec2 star_pos = hash22(cell);
            float star_brightness = hash21(cell + 1.0) * STAR_BRIGHTNESS;
            
            // Calculate distance from fragment to the star.
            float dist = length(frag_id - star_pos + vec2(x, y));
            
            // Draw the star using a smooth falloff.
            float star_glow = smoothstep(0.05, 0.0, dist);
            float star_core = smoothstep(0.01, 0.0, dist);
            
            // Get the star's color from the palette.
            vec3 star_color = cosPalette(cell_hash, PALETTE);
            
            // Add the star's light to the final color.
            finalColor += (star_glow * 0.5 + star_core) * star_brightness * star_color;
        }
    }

    // 3. Meteor Generation.
    // Divide time into longer, less frequent "epochs".
    float epoch_duration = 20.0; // A potential meteor can occur every 20 seconds.
    float epoch = floor(uTime / epoch_duration);
    
    // Use a hash of the epoch to decide if a meteor appears at all.
    float meteor_probability = 0.4; // 40% chance of a meteor per epoch.
    if (hash21(vec2(epoch)) < meteor_probability) {
        float time_in_epoch = mod(uTime, epoch_duration);
        float meteor_duration = 8.0; // The meteor is visible for 8 seconds.
        
        if (time_in_epoch < meteor_duration) {
            // Use another hash to randomize the meteor's path for this epoch.
            vec2 path_rand = hash22(vec2(epoch));
            vec2 start_pos = vec2(-7.0, -7.0 + path_rand.x * 14.0);
            vec2 end_pos = vec2(7.0, -7.0 + path_rand.y * 14.0);
            vec2 path = end_pos - start_pos;
            
            // Calculate the meteor's lifetime progress (0.0 to 1.0).
            float life = time_in_epoch / meteor_duration;
            
            // The head of the meteor trail.
            vec2 trail_end = start_pos + path * life;
            // The tail of the meteor trail, which is slightly behind the head.
            vec2 trail_start = trail_end - normalize(path) * 2.0; // 2.0 is the trail length.

            // Calculate distance to the trail segment.
            float dist = distToSegment(uv, trail_start, trail_end);
            
            // Draw the meteor with a bright core and a soft glow.
            float meteor_glow = smoothstep(0.3, 0.0, dist);
            float meteor_core = smoothstep(0.03, 0.0, dist);
            
            // Make the entire trail fade in and out smoothly during its cycle.
            float fade = sin(life * PI);
            
            // Make the trail fade out towards its tail.
            float trail_fade = smoothstep(0.0, 1.0, dot(uv - trail_start, normalize(path)) / length(trail_end - trail_start));
            
            finalColor += (meteor_glow * 0.2 + meteor_core) * vec3(0.8, 1.0, 1.0) * fade * trail_fade * 3.0;
        }
    }

    // 4. Tonemapping and Output.
    finalColor = 1.0 - exp(-finalColor); // Simple exposure control.
    finalColor = pow(finalColor, vec3(0.8)); // Gamma correction.

    return vec4(finalColor, 1.0);
}
    """
}