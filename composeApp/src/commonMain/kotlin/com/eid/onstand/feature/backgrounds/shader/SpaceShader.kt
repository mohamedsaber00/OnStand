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
 * Peaceful 2D Starfield + “Old Style” Cinematic Meteor (Fixed)
 *
 * - Starfield drifts (uvStars) exactly as before.
 * - Meteor uses OLD chord style (two points on a circle) with original
 *   glow/core distances and lingering tail color logic.
 * - Meteor path & timing computed in static space (uvBase) so drift
 *   never knocks it off-screen prematurely.
 * - Visibility window trims the chord to the portion intersecting
 *   the screen rectangle, so the meteor is only drawn while it is
 *   potentially visible.
 */

uniform float uTime;
uniform vec3  uResolution;

// -------------------- Starfield Config --------------------
const float SPEED           = 0.10;
const float STAR_DENSITY    = 1.5;   // >1 => every cell spawns a star (kept as you had)
const float STAR_BRIGHTNESS = 8.0;
const int   PALETTE         = -1;    // -1 = mixed palettes, 0..4 fixed

// -------------------- Meteor Timing/Look ------------------
const float METEOR_EPOCH_LENGTH = 25.0;  // window per meteor opportunity
const float METEOR_MIN_DELAY    = 0.5;   // earliest start inside epoch
const float METEOR_MIN_DURATION = 9.0;
const float METEOR_MAX_DURATION = 12.0;

const float METEOR_RADIUS_MARGIN = 0.99;  // extra radius outside view (old style)
const float METEOR_DELTA_VARIANCE = 0.35; // angular variance added to PI

// Old style visual parameters
const float METEOR_CORE_R   = 0.03;
const float METEOR_GLOW_R   = 0.30;
const float METEOR_TRAIL_LEN= 3.0;
const float METEOR_INTENSITY= 7.0;
const float METEOR_LINGER   = 0.10;   // fraction of extra glow lingering
const float METEOR_COLOR_LERP_EXP = 0.35; // tail->head color bias

// -------------------- Tonemap / Gamma ---------------------
const float OUTPUT_GAMMA = 0.8;

// -------------------- Math / Helpers ----------------------
const float PI  = 3.1415926535;
const float TAU = 6.283185307;
const float WORLD_SCALE = 10.0; // matches your original uv scaling

float hash21(vec2 p){
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}
vec2 hash22(vec2 p){
    float n = sin(dot(p, vec2(41.0, 289.0)));
    return fract(vec2(262144.0, 32768.0) * n);
}
float distToSegment(vec2 p, vec2 a, vec2 b){
    vec2 pa = p - a, ba = b - a;
    float h = clamp(dot(pa,ba)/dot(ba,ba), 0.0, 1.0);
    return length(pa - ba*h);
}
vec3 cosPalette(float t, int paletteId){
    if(paletteId == 0) return vec3(1.0);
    vec3 a,b,c,d;
    if(paletteId==1){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0);       d=vec3(0.00,0.10,0.20); }
    else if(paletteId==2){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0,0.7,0.4); d=vec3(0.00,0.15,0.20); }
    else if(paletteId==3){ a=vec3(0.5); b=vec3(0.5); c=vec3(1.0);       d=vec3(0.30,0.40,0.50); }
    else if(paletteId==4){ a=vec3(0.8,0.5,0.4); b=vec3(0.2,0.4,0.2); c=vec3(2.0,1.0,1.0); d=vec3(0.00,0.25,0.25); }
    else return vec3(1.0);
    return a + b * cos(TAU * (c*t + d));
}

// Clamp star density to avoid out-of-range logic (kept but optional)
float effectiveStarDensity() {
    return (STAR_DENSITY < 0.0) ? 0.0 : STAR_DENSITY;
}

// Compute intersection (parameter t range) of a parametric line segment
// P(t) = start + dir * t, t in [0, segLen], with axis aligned rectangle.
// Returns (tMinVisible, tMaxVisible) clamped to [0,segLen]; if no overlap,
// tMin > tMax.
vec2 chordVisibleInterval(vec2 start, vec2 dir, float segLen, float halfW, float halfH){
    float tMin = 0.0;
    float tMax = segLen;

    // For each axis: solve start.xy + dir.xy * t inside [-half?, half?]
    // x limits
    if (abs(dir.x) < 1e-5){
        if (abs(start.x) > halfW) return vec2(1.0, -1.0); // no intersection
    } else {
        float tx1 = (-halfW - start.x)/dir.x;
        float tx2 = ( halfW - start.x)/dir.x;
        if (tx1 > tx2){ float tmp=tx1; tx1=tx2; tx2=tmp; }
        tMin = max(tMin, tx1);
        tMax = min(tMax, tx2);
    }
    // y limits
    if (abs(dir.y) < 1e-5){
        if (abs(start.y) > halfH) return vec2(1.0, -1.0);
    } else {
        float ty1 = (-halfH - start.y)/dir.y;
        float ty2 = ( halfH - start.y)/dir.y;
        if (ty1 > ty2){ float tmp=ty1; ty1=ty2; ty2=tmp; }
        tMin = max(tMin, ty1);
        tMax = min(tMax, ty2);
    }

    // Clamp to segment
    tMin = max(tMin, 0.0);
    tMax = min(tMax, segLen);
    return vec2(tMin, tMax);
}

// -------------------- Main -------------------------------
vec4 main(vec2 fragCoord){
    // Base static UV (for meteor geometry)
    vec2 uvBase  = (fragCoord - uResolution.xy * 0.5) / uResolution.y * WORLD_SCALE;
    // Drifting UV for the starfield only
    vec2 uvStars = uvBase + vec2(uTime * SPEED, uTime * SPEED * 0.5);

    vec3 finalColor = vec3(0.0);

    // --------- Starfield (original behavior) ---------
    vec2 grid_id = floor(uvStars);
    vec2 frag_id = fract(uvStars);
    float density = effectiveStarDensity();

    for (float y=-1.0; y<=1.0; y++){
        for (float x=-1.0; x<=1.0; x++){
            vec2 cell = grid_id + vec2(x,y);
            float cell_hash = hash21(cell);
            if (cell_hash > density) continue;

            vec2 star_pos = hash22(cell);
            float star_brightness = hash21(cell + 1.0) * STAR_BRIGHTNESS;
            float dist = length(frag_id - star_pos + vec2(x,y));

            float star_glow = smoothstep(0.05, 0.0, dist);
            float star_core = smoothstep(0.01, 0.0, dist);

            float twinkle = 0.75 + 0.25 * sin(uTime * 2.0 + cell_hash * 10.0);

            int paletteToUse = (PALETTE < 0)
                ? int(floor(hash21(cell) * 5.0))
                : PALETTE;

            vec3 star_color = cosPalette(cell_hash, paletteToUse);

            finalColor += (star_glow * 0.5 + star_core)
                        * star_brightness * star_color * twinkle;
        }
    }

    // --------- Old Style Meteor (fixed) ---------------
    {
        float epoch = floor(uTime / METEOR_EPOCH_LENGTH);
        float tIn   = mod(uTime, METEOR_EPOCH_LENGTH);

        // Random duration & start inside epoch
        float durSeed   = hash21(vec2(epoch, 3.0));
        float meteorDur = mix(METEOR_MIN_DURATION, METEOR_MAX_DURATION, durSeed);
        float latestStart = METEOR_EPOCH_LENGTH - meteorDur;
        float startSeed   = hash21(vec2(epoch, 5.0));
        float startTime   = METEOR_MIN_DELAY
                          + startSeed * max(0.0, latestStart - METEOR_MIN_DELAY);

        bool active = (tIn >= startTime) && (tIn < startTime + meteorDur);

        if (active){
            float life = (tIn - startTime)/meteorDur; // 0..1 base life

            // Circular chord endpoints (old style)
            float baseAngle  = hash21(vec2(epoch, 10.0)) * TAU;
            float deltaAngle = PI + (hash21(vec2(epoch, 99.0)) - 0.5) * 2.0 * METEOR_DELTA_VARIANCE;

            // Screen rectangle half extents
            float halfH = WORLD_SCALE * 0.5;
            float aspect = uResolution.x / uResolution.y;
            float halfW = halfH * aspect;

            float rad = min(halfW, halfH) + METEOR_RADIUS_MARGIN;

            vec2 start_pos = vec2(cos(baseAngle),        sin(baseAngle))        * rad;
            vec2 end_pos   = vec2(cos(baseAngle+deltaAngle), sin(baseAngle+deltaAngle)) * rad;

            vec2 chord = end_pos - start_pos;
            float chordLen = length(chord);
            vec2 dir = chord / chordLen;

            // Visible interval of chord inside rectangle
            vec2 tInterval = chordVisibleInterval(start_pos, dir, chordLen, halfW, halfH);
            float tMinVis = tInterval.x;
            float tMaxVis = tInterval.y;

            if (tMinVis <= tMaxVis){
                float visibleSpan = max(tMaxVis - tMinVis, 1e-4);

                // Map life (0..1) onto *visible* segment only
                float tAlong = tMinVis + life * visibleSpan;
                vec2 head = start_pos + dir * tAlong;
                vec2 tail = head - dir * METEOR_TRAIL_LEN;

                float d = distToSegment(uvBase, tail, head);
                float meteor_glow = smoothstep(METEOR_GLOW_R, 0.0, d);
                float meteor_core = smoothstep(METEOR_CORE_R, 0.0, d);

                // Temporal bell fade
                float fade = sin(life * PI); fade *= fade;

                // Along-tail fade
                float along = clamp(dot(uvBase - tail, dir) / length(head - tail), 0.0, 1.0);
                float trail_fade = smoothstep(0.0, 1.0, along);

                // Color (old style logic, using palettes)
                int meteorPalette = int(floor(hash21(vec2(epoch, 55.0)) * 5.0));
                vec3 headColor = cosPalette(life, meteorPalette);
                vec3 tailColor = cosPalette(life * 0.3 + 0.2, meteorPalette);
                vec3 meteorColor = mix(headColor, tailColor, pow(along, METEOR_COLOR_LERP_EXP));

                // Main light
                finalColor += (meteor_glow * 0.2 + meteor_core) *
                              meteorColor * fade * trail_fade * METEOR_INTENSITY;

                // Linger (soft residual)
                finalColor += meteor_glow * METEOR_LINGER * meteorColor * (1.0 - life);
            }
        }
    }

    // --------- Tonemap & Gamma -----------
    finalColor = 1.0 - exp(-finalColor);
    finalColor = pow(finalColor, vec3(OUTPUT_GAMMA));

    return vec4(finalColor, 1.0);
}
    """
}