package com.eid.onstand.feature.backgrounds.shader

import com.mikepenz.hypnoticcanvas.shaders.Shader

object MovingTrianglesShader : Shader {
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
 * Modern Clock App Background Shader
 *
 * This SkSL shader generates a smooth, animated background. It features:
 * - A diagonal gradient from a deep blue to a light pastel blue.
 * - A soft, white beam of light that rotates slowly and continuously.
 * - Procedural generation, requiring no external textures.
 *
 * Uniforms:
 * - uTime: The total elapsed time in seconds, used to drive the animation.
 * - uResolution: The dimensions of the fragment area (width, height, pixel_density).
 */

uniform float uTime;
uniform vec3 uResolution;

// Function to create a smooth, rotating line.
// 'p' is the coordinate, 'angle' is the rotation, 'thickness' controls the line width.
float rotatingLine(vec2 p, float angle, float thickness) {
    // Create a rotation matrix
    mat2 rotationMatrix = mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
    // Apply the rotation to the coordinates
    vec2 rotatedP = p * rotationMatrix;
    // The line is now horizontal. We calculate the distance from the x-axis.
    float dist = abs(rotatedP.y);
    // Use smoothstep for a soft, anti-aliased edge.
    // The line is drawn where dist < thickness, with a soft falloff.
    return 1.0 - smoothstep(thickness - 0.01, thickness + 0.01, dist);
}

vec4 main(vec2 fragCoord) {
    // 1. Normalize coordinates to a [0, 1] range.
    // This makes the shader resolution-independent.
    vec2 uv = fragCoord.xy / uResolution.xy;

    // 2. Define the gradient colors.
    vec3 colorA = vec3(0.1, 0.2, 0.4); // Deep Blue
    vec3 colorB = vec3(0.7, 0.8, 1.0); // Light Pastel Blue

    // 3. Create the diagonal background gradient.
    // We mix the two colors based on the sum of the normalized coordinates.
    // The `* 0.6` factor adjusts the gradient's spread across the screen.
    vec3 bgColor = mix(colorA, colorB, (uv.x + uv.y) * 0.6);

    // 4. Create the rotating light beam.
    // Center the coordinates so rotation happens around the screen's center.
    vec2 centeredUv = uv - 0.5;
    // Scale coordinates to maintain aspect ratio (important for a circular beam path).
    centeredUv.x *= uResolution.x / uResolution.y;

    // Define the beam's properties.
    float beamThickness = 0.08; // How wide the beam is.
    float beamAngle = uTime * 0.25; // Controls the rotation speed.
    vec3 beamColor = vec3(1.0, 1.0, 1.0); // Pure white light.

    // Calculate the beam's intensity at the current fragment.
    float beamIntensity = rotatingLine(centeredUv, beamAngle, beamThickness);
    // Modulate the intensity to make it softer in the center.
    beamIntensity *= 0.25; // Reduce overall brightness to make it a "soft" beam.

    // 5. Combine the background and the beam.
    // We use additive blending to make it look like light is being cast on the background.
    vec3 finalColor = bgColor + beamColor * beamIntensity;

    // 6. Return the final color with full alpha.
    return vec4(finalColor, 1.0);
}

    """
}