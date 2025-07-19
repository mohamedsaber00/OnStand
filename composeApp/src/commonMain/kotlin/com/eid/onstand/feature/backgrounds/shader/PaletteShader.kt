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
  Palette Shader (Subtly Slower, Slight De-Sync) + Liquid Glass Colors
  uPalette:
    0 = LiquidGlass (new color scheme)
    1 = Black-Red (original)
    2 = Neon (original)
--------------------------------------------------------------*/
uniform float  uTime;
uniform float3 uResolution;
uniform int    uPalette;
uniform float  uSpeed;   // 1.0 original feel, 0.5 slower, 0.25 calm
uniform float  uSeed;    // optional randomization seed (can be 0)

float hash(float n){ return fract(sin(n)*43758.5453123); }

/* ---------- Original Palettes (kept for ids 1 & 2) ---------- */
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

/* ---------- Liquid Glass Multi-stop Gradient ---------- */
float3 liquidGlassGradient(float x){
    x = clamp(x,0.0,1.0);
    // Color stops (approximate to reference image)
    const float3 c1 = float3(0.07, 0.10, 0.35); // deep indigo
    const float3 c2 = float3(0.30, 0.48, 0.95); // cool blue
    const float3 c3 = float3(0.62, 0.50, 0.95); // lavender
    const float3 c4 = float3(0.96, 0.53, 0.70); // pink
    const float3 c5 = float3(1.00, 0.68, 0.45); // peach
    const float3 c6 = float3(1.00, 0.86, 0.72); // warm cream
    
    // Segment boundaries
    if (x < 0.15) return mix(c1, c2, x/0.15);
    else if (x < 0.32) return mix(c2, c3, (x-0.15)/(0.17));
    else if (x < 0.52) return mix(c3, c4, (x-0.32)/(0.20));
    else if (x < 0.72) return mix(c4, c5, (x-0.52)/(0.20));
    else return mix(c5, c6, (x-0.72)/(0.28));
}

/* Combines intensity + horizontal factor to approximate smooth layered glass */
float3 paletteLiquidGlass(float t, float2 uv){
    // Horizontal factor mapped to 0..1 (slightly biased to show more warm on right)
    float hx = clamp(uv.x * 0.45 + 0.5, 0.0, 1.0);
    // Blend intensity & position for gradient coordinate
    float gCoord = clamp(mix(hx, t, 0.40) + 0.10 * sin(t*6.28318)*0.02, 0.0, 1.0);
    float3 base = liquidGlassGradient(gCoord);
    // Highlight bloom (subtle creamy lift)
    float highlight = smoothstep(0.55, 0.85, t);
    base += highlight * float3(0.20, 0.18, 0.15);
    // Gentle softening
    base = mix(base, pow(base, float3(0.8)), 0.35);
    return clamp(base, 0.0, 1.0);
}

/* ---------- Palette Selector ---------- */
float3 pickPalette(float intensity, float2 uv, int id){
    if(id==1) return paletteBlackRed(intensity);
    if(id==2) return paletteNeon(intensity);
    // id==0 or default -> new liquid glass palette
    return paletteLiquidGlass(intensity, uv);
}

/* ---------- Main (animation logic unchanged) ---------- */
vec4 main(vec2 fragCoord){
    float2 res = uResolution.xy;
    float  minSide = min(res.x,res.y);
    float2 uv = (fragCoord*2.0 - res)/minSide;

    float speed = max(uSpeed, 0.0);
    // identical slowdown factors
    float tBase = uTime * 0.24 * (speed + 0.2);
    float tAux  = uTime * 0.17 * (speed + 0.2);

    const int ITERATIONS = 8;
    float d   = -tBase;
    float acc = 0.0;

    for(int i=0;i<ITERATIONS;++i){
        float fi = float(i);
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

    // Keep the subtle gamma
    finalColor = pow(finalColor, float3(0.9));
    return float4(finalColor,1.0);
}
    """
}