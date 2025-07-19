package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object PaletteShader : Shader {
    override val name: String
        get() = "Palette"

    override val authorName: String
        get() = "Unknown (ported by you)"

    override val authorUrl: String
        get() = ""

    override val credit: String
        get() = "Palette Shader with customizable color schemes"

    override val license: String
        get() = "Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License"

    override val licenseUrl: String
        get() = "https://creativecommons.org/licenses/by-nc-sa/3.0/"

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/*--------------------------------------------------------------
  Palette Shader (Version A Motion) – Vivid Liquid Glass Colors
  Distortion fix: ONLY coordinate pre-processing changed.
--------------------------------------------------------------*/
uniform float  uTime;
uniform float3 uResolution;
uniform int    uPalette;
uniform float  uSpeed;     // 1.0 original feel, 0.5 slower, 0.25 calm
uniform float  uSeed;      // phase randomization seed
uniform float  uVivid;     // palette 0 vividness
uniform float  uBloom;     // palette 0 bloom
uniform float  uHueDrift;  // palette 0 hue drift
uniform float  uWarmBias;  // palette 0 warm/cool bias

float hash(float n){ return fract(sin(n)*43758.5453123); }

/* ---------- Original Non-Liquid Palettes (unchanged) ---------- */
float3 paletteBlackRed(float t){
    float3 a=float3(0.2,0.0,0.0);
    float3 b=float3(0.8,0.2,0.1);
    float3 c=float3(1.0); float3 d=float3(0.0,0.5,0.8);
    return a + b * cos(6.28318 * (c*t + d));
}
float3 paletteNeon(float t){
    t = clamp(t,0.0,1.0);
    if(t<0.6) return float3(0.0);
    else if(t<0.8){
        float k=(t-0.6)/0.2;
        return float3(k*2.0,0.0,k*0.6);
    } else {
        float k=(t-0.8)/0.2;
        return float3(0.6*(1.0-k)+k*0.2,
                      0.0,
                      1.4*k + 0.6*(1.0-k));
    }
}

/* ---------- Hue rotation helper ---------- */
float3 hueRotate(float3 c, float a){
    const float3 w = float3(0.299,0.587,0.114);
    float Y = dot(c,w);
    float3 d = c - Y;
    float cs = cos(a), sn = sin(a);
    float3 u = normalize(float3( 0.787, -0.615, -0.072));
    float3 v = normalize(cross(float3(0.0,0.0,1.0), u));
    float du = dot(d,u), dv = dot(d,v);
    d = u*(du*cs - dv*sn) + v*(du*sn + dv*cs);
    return clamp(Y + d, 0.0, 1.0);
}

/* ---------- Gradient stops for vivid palette ---------- */
float3 vividGradient(float x){
    x = clamp(x,0.0,1.0);
    const float3 c1 = float3(0.02, 0.05, 0.28);
    const float3 c2 = float3(0.08, 0.32, 0.90);
    const float3 c3 = float3(0.48, 0.32, 0.95);
    const float3 c4 = float3(0.95, 0.30, 0.70);
    const float3 c5 = float3(1.00, 0.58, 0.30);
    const float3 c6 = float3(1.00, 0.82, 0.55);
    if (x < 0.14) return mix(c1,c2,x/0.14);
    else if (x < 0.30) return mix(c2,c3,(x-0.14)/0.16);
    else if (x < 0.50) return mix(c3,c4,(x-0.30)/0.20);
    else if (x < 0.70) return mix(c4,c5,(x-0.50)/0.20);
    else return mix(c5,c6,(x-0.70)/0.30);
}

float3 paletteLiquidGlassVivid(float t, float2 uv){
    float hx = clamp(uv.x * 0.50 + 0.5, 0.0, 1.0);
    float gCoord = clamp(mix(hx, t, 0.35), 0.0, 1.0);
    gCoord += sin((t + hx)*6.28318)*0.01;
    float3 base = vividGradient(gCoord);

    float bloomAmt = smoothstep(0.55,0.85,t) * clamp(uBloom,0.0,1.0);
    float3 bloomCol = float3(1.0, 0.95, 0.90);
    base = mix(base, clamp(base + bloomCol * bloomAmt * 0.8, 0.0, 1.0), 0.6);

    float vivid = clamp(uVivid,0.0,1.0);
    float l = dot(base,float3(0.299,0.587,0.114));
    base = mix(base, mix(float3(l), base, 1.0 + vivid*0.8), vivid);
    base = mix(base, (base - 0.5)*(1.0 + vivid*0.6) + 0.5, vivid*0.75);

    float wb = clamp(uWarmBias,-1.0,1.0);
    if (wb != 0.0){
        float3 cool = float3(0.10,0.18,0.45);
        float3 warm = float3(1.05,0.80,0.50);
        float3 target = (wb > 0.0) ? warm : cool;
        base = mix(base, target, abs(wb)*0.20);
    }

    float drift = uHueDrift * 0.8;
    if (drift > 0.0){
        float angle = uTime * 0.07 * drift;
        base = hueRotate(base, angle);
    }

    base = pow(base, float3(0.92));
    return clamp(base,0.0,1.0);
}

float3 pickPalette(float intensity, float2 uv, int id){
    if(id==1) return paletteBlackRed(intensity);
    if(id==2) return paletteNeon(intensity);
    return paletteLiquidGlassVivid(intensity, uv);
}

/* ----------------- MAIN ----------------- */
vec4 main(vec2 fragCoord){
    float2 res = uResolution.xy;
    float  minSide = min(res.x,res.y);

    // --- Distortion Fix Block (only change) ---
    float2 uv = (fragCoord*2.0 - res)/minSide;   // original normalization
    float aspect = res.x / res.y;

    // Strength of aspect neutralization (1 = full)
    const float ASPECT_STRENGTH = 0.85;
    float ax = mix(uv.x, uv.x / aspect, ASPECT_STRENGTH);

    // Soft compression params
    const float EDGE_START = 0.86;  // start compressing beyond this |x|
    const float EDGE_PUSH  = 0.55;  // how strongly to push edges inward

    float absx = abs(ax);
    float edgeT = smoothstep(EDGE_START, 1.0, absx);
    // Non-linear ease for softer transition
    edgeT = edgeT*edgeT*(3.0 - 2.0*edgeT);
    float compressed = ax * mix(1.0, (EDGE_START + (absx-EDGE_START)*0.4)/absx, edgeT * EDGE_PUSH);

    ax = (absx > EDGE_START) ? compressed : ax;

    // (Optional micro un-warp; enable if you want even calmer edges)
    // ax -= 0.015 * edgeT * sin(ax * 6.28318);

    uv.x = ax;
    // --- End Fix Block ---

    float speed = max(uSpeed, 0.0);
    float tBase = uTime * 0.24 * (speed + 0.2);
    float tAux  = uTime * 0.17 * (speed + 0.2);

    const int ITERATIONS = 8;
    float d=-tBase, acc=0.0;
    for(int i=0;i<ITERATIONS;++i){
        float fi=float(i);
        float phase = hash(fi + uSeed*13.17)*6.28318;
        acc += cos(fi - d - acc * uv.x + phase*0.15);
        d   += sin(uv.y * fi + acc + phase*0.10);
    }
    d += tBase;

    float2 freq = float2(d + 0.15*sin(tAux),
                         acc + 0.10*cos(tAux*0.7));
    float3 rawCol = float3(
        cos(uv.x * freq.x)*0.6 + 0.4,
        cos(uv.y * freq.y)*0.6 + 0.4,
        cos(acc + d + 0.25*sin(tAux))*0.5 + 0.5
    );

    float intensity = clamp((rawCol.r+rawCol.g+rawCol.b)/3.0, 0.0, 1.0);
    float3 finalColor = pickPalette(intensity, uv, uPalette);
    finalColor = pow(finalColor, float3(0.9));
    return float4(finalColor,1.0);
}
    """
}