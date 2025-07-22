package com.eid.onstand.feature.backgrounds.shader

import com.eid.onstand.core.shaders.Shader


object RedShader : Shader {

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/*--------------------------------------------------------------
 Red Shader : Same as Palette but with different colors
--------------------------------------------------------------*/
uniform float  uTime;
uniform float3 uResolution;
uniform int    uPalette;
uniform float  uSpeed;
uniform float  uSeed;

uniform float  uVivid;     
uniform float  uBloom;     
uniform float  uHueDrift;  
uniform float  uWarmBias;  

float hash(float n){ return fract(sin(n)*43758.5453123); }

/* ---------- Palettes 1 & 2 (unchanged) ---------- */
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

float3 hueRotate(float3 c, float a){
    const float3 w = float3(0.299,0.587,0.114);
    float Y = dot(c,w);
    float3 d = c - Y;
    float cs = cos(a), sn = sin(a);
    float3 u = normalize(float3( 0.787,-0.615,-0.072));
    float3 v = normalize(cross(float3(0.0,0.0,1.0), u));
    float du = dot(d,u), dv = dot(d,v);
    d = u*(du*cs - dv*sn) + v*(du*sn + dv*cs);
    return clamp(Y + d, 0.0, 1.0);
}

float3 coralMurreyGradient(float g){
    g = clamp(g,0.0,1.0);
    // Source colors (sRGB-ish)
    const float3 blackWash = float3(0.051,0.051,0.051); // #0d0d0d
    const float3 murrey    = float3(0.439,0.133,0.274); // #702246
    const float3 lightCoral= float3(0.949,0.522,0.557); // #f2858e

    // Position of mid transition
    float mid = 0.50;

    // Smooth tri-blend:
    // t1 grows 0→1 up to mid; t2 grows 0→1 after mid.
    float t1 = smoothstep(0.0, mid, g);
    float t2 = smoothstep(mid*0.9, 1.0, g);

    float3 base = mix(blackWash, murrey, t1);
    base = mix(base, lightCoral, t2);

    return base;
}

float3 paletteCoralMurrey(float intensity, float2 uv){
    float hx = clamp(uv.x * 0.50 + 0.5, 0.0, 1.0);
    float gCoord = clamp(mix(hx, intensity, 0.35), 0.0, 1.0);
    // Slight micro variation (same style)
    gCoord += sin((intensity + hx)*6.28318)*0.01;

    float3 base = coralMurreyGradient(gCoord);

    float bloomAmt = smoothstep(0.55,0.85,intensity) * clamp(uBloom,0.0,1.0);
    float3 bloomCol = float3(1.0,0.92,0.94);
    base = mix(base, clamp(base + bloomCol * bloomAmt * 0.6,0.0,1.0), 0.55);

    float vivid = clamp(uVivid,0.0,1.0);
    float l = dot(base,float3(0.299,0.587,0.114));
    base = mix(base, mix(float3(l), base, 1.0 + vivid*0.6), vivid*0.85);
    base = mix(base, (base - 0.5)*(1.0 + vivid*0.4) + 0.5, vivid*0.6);

    float wb = clamp(uWarmBias,-1.0,1.0);
    if(wb!=0.0){
        float3 cool = float3(0.10,0.16,0.25);
        float3 warm = float3(0.96,0.58,0.50);
        base = mix(base, wb>0.0 ? warm : cool, abs(wb)*0.15);
    }

    float drift = uHueDrift * 0.6;
    if(drift > 0.0){
        float angle = uTime * 0.05 * drift;
        base = hueRotate(base, angle);
    }

    base = pow(base, float3(0.92));

    return clamp(base,0.0,1.0);
}

float3 pickPalette(float intensity, float2 uv, int id){
    if(id==1) return paletteBlackRed(intensity);
    if(id==2) return paletteNeon(intensity);
    // id==0 (or fallback)
    return paletteCoralMurrey(intensity, uv);
}

/* ----------------- MAIN ----------------- */
vec4 main(vec2 fragCoord){
    float2 res = uResolution.xy;
    float  minSide = min(res.x,res.y);

    float2 uv = (fragCoord*2.0 - res)/minSide;
    float aspect = res.x / res.y;
    const float ASPECT_STRENGTH = 0.85;
    float ax = mix(uv.x, uv.x / aspect, ASPECT_STRENGTH);
    const float EDGE_START = 0.86;
    const float EDGE_PUSH  = 0.55;
    float absx = abs(ax);
    float edgeT = smoothstep(EDGE_START, 1.0, absx);
    edgeT = edgeT*edgeT*(3.0 - 2.0*edgeT);
    float compressed = ax * mix(1.0, (EDGE_START + (absx-EDGE_START)*0.4)/absx,
                                edgeT * EDGE_PUSH);
    ax = (absx > EDGE_START) ? compressed : ax;
    uv.x = ax;

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