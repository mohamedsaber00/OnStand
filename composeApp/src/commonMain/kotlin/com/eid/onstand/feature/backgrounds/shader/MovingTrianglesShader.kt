package com.eid.onstand.feature.backgrounds.shader

import com.eid.onstand.core.ui.shaders.Shader

object MovingTrianglesShader : Shader {

    override val speedModifier: Float
        get() = 1.0f

    override val sksl = """
/**
 * Ice and Fire, by mattz
 * License Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 *
 * Converted from GLSL to SkSL.
 * This shader demonstrates the triangulation of a jittered triangular lattice.
 *
 * Uniforms:
 * - uTime: The total elapsed time in seconds, used to drive the animation.
 * - uResolution: The dimensions of the fragment area (width, height, pixel_density).
 */

uniform float uTime;
uniform vec3 uResolution;

const float s3 = 1.7320508075688772;
const float i3 = 0.5773502691896258;

const mat2 tri2cart = mat2(1.0, 0.0, -0.5, 0.5*s3);
const mat2 cart2tri = mat2(1.0, 0.0, i3, 2.0*i3);

const float HASHSCALE1 = 0.1031;
const vec3 HASHSCALE3 = vec3(443.897, 441.423, 437.195);

//////////////////////////////////////////////////////////////////////
vec3 pal(in float t) {
    const vec3 a = vec3(0.5);
    const vec3 b = vec3(0.5);
    const vec3 c = vec3(0.8, 0.8, 0.5);
    const vec3 d = vec3(0.0, 0.2, 0.5);
    return clamp(a + b*cos(6.28318*(c*t+d)), 0.0, 1.0);
}

//////////////////////////////////////////////////////////////////////
float hash12(vec2 p) {
    vec3 p3  = fract(vec3(p.xyx) * HASHSCALE1);
    p3 += dot(p3, p3.yzx + 19.19);
    return fract((p3.x + p3.y) * p3.z);
}

vec2 hash23(vec3 p3) {
	p3 = fract(p3 * HASHSCALE3);
    p3 += dot(p3, p3.yzx+19.19);
    return fract((p3.xx+p3.yz)*p3.zy);
}

//////////////////////////////////////////////////////////////////////
vec3 bary(vec2 v0, vec2 v1, vec2 v2) {
    float inv_denom = 1.0 / (v0.x * v1.y - v1.x * v0.y);
    float v = (v2.x * v1.y - v1.x * v2.y) * inv_denom;
    float w = (v0.x * v2.y - v2.x * v0.y) * inv_denom;
    float u = 1.0 - v - w;
    return vec3(u,v,w);
}

//////////////////////////////////////////////////////////////////////
float dseg(vec2 xa, vec2 ba) {
    return length(xa - ba*clamp(dot(xa, ba)/dot(ba, ba), 0.0, 1.0));
}

//////////////////////////////////////////////////////////////////////
vec2 randCircle(vec3 p) {
    vec2 rt = hash23(p);
    float r = sqrt(rt.x);
    float theta = 6.283185307179586 * rt.y;
    return r*vec2(cos(theta), sin(theta));
}

//////////////////////////////////////////////////////////////////////
vec2 randCircleSpline(vec2 p, float t) {
    // standard catmull-rom spline implementation
    float t1 = floor(t);
    t -= t1;

    vec2 pa = randCircle(vec3(p, t1-1.0));
    vec2 p0 = randCircle(vec3(p, t1));
    vec2 p1 = randCircle(vec3(p, t1+1.0));
    vec2 pb = randCircle(vec3(p, t1+2.0));

    vec2 m0 = 0.5*(p1 - pa);
    vec2 m1 = 0.5*(pb - p0);

    vec2 c3 = 2.0*p0 - 2.0*p1 + m0 + m1;
    vec2 c2 = -3.0*p0 + 3.0*p1 - 2.0*m0 - m1;
    vec2 c1 = m0;
    vec2 c0 = p0;

    return (((c3*t + c2)*t + c1)*t + c0) * 0.8;
}

//////////////////////////////////////////////////////////////////////
vec2 triPoint(vec2 p) {
    float t0 = hash12(p);
    return tri2cart*p + 0.45*randCircleSpline(p, 0.15*uTime + t0);
}

//////////////////////////////////////////////////////////////////////
void tri_color(in vec2 p,
               in vec4 t0, in vec4 t1, in vec4 t2,
               in float scl,
               inout vec4 cw) {

    // get differences relative to vertex 0
    vec2 p0 = p - t0.xy;
    vec2 p10 = t1.xy - t0.xy;
    vec2 p20 = t2.xy - t0.xy;

    // get barycentric coords
    vec3 b = bary(p10, p20, p0);

    // distances to line segments
    float d10 = dseg(p0, p10);
    float d20 = dseg(p0, p20);
    float d21 = dseg(p - t1.xy, t2.xy - t1.xy);

    // unsigned distance to triangle boundary
    float d = min(min(d10, d20), d21);

    // now signed distance (negative inside, positive outside)
    d *= -sign(min(b.x, min(b.y, b.z)));

    // only worry about coloring if close enough
    if (d < 0.5*scl) {
        vec2 tsum = t0.zw + t1.zw + t2.zw;
        vec3 h_tri = vec3(hash12(tsum + t0.zw),
                          hash12(tsum + t1.zw),
                          hash12(tsum + t2.zw));

        vec2 pctr = (t0.xy + t1.xy + t2.xy) / 3.0;
        float theta = 1.0 + 0.01*uTime;
        vec2 dir = vec2(cos(theta), sin(theta));
        float grad_input = dot(pctr, dir) - sin(0.05*uTime);
        float h0 = sin(0.7*grad_input)*0.5 + 0.5;
        h_tri = mix(vec3(h0), h_tri, 0.4);

        float h = dot(h_tri, b);
        vec3 c = pal(h);
        float w = smoothstep(0.5*scl, -0.5*scl, d);
        cw += vec4(w*c, w);
    }
}

// Main entry point for SkSL
vec4 main(vec2 fragCoord) {
    float scl = 4.1 / uResolution.y;

    // get 2D scene coords
    vec2 p = (fragCoord - 0.5*uResolution.xy) * scl;

    // get triangular base coords
    vec2 tfloor = floor(cart2tri * p + 0.5);

    // precompute 9 neighboring points
    vec2 pts[9];
    for (int i=0; i<3; ++i) {
        for (int j=0; j<3; ++j) {
            pts[3*i+j] = triPoint(tfloor + vec2(i-1, j-1));
        }
    }

    // color accumulator
    vec4 cw = vec4(0);

    // for each of the 4 quads:
    for (int i=0; i<2; ++i) {
        for (int j=0; j<2; ++j) {
            // look at lower and upper triangle in this quad
            vec4 t00 = vec4(pts[3*i+j  ], tfloor + vec2(i-1, j-1));
            vec4 t10 = vec4(pts[3*i+j+3], tfloor + vec2(i,   j-1));
            vec4 t01 = vec4(pts[3*i+j+1], tfloor + vec2(i-1, j));
            vec4 t11 = vec4(pts[3*i+j+4], tfloor + vec2(i,   j));

            // lower
            tri_color(p, t00, t10, t11, scl, cw);
            // upper
            tri_color(p, t00, t11, t01, scl, cw);
        }
    }

    // final pixel color, check for division by zero
    if (cw.w <= 0.0) {
        return vec4(0.0, 0.0, 0.0, 1.0);
    }
    return vec4(cw.xyz / cw.w, 1.0);
}


    """
}